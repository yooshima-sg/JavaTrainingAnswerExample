package com.s_giken.training.batch.resolver.basic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.s_giken.training.batch.resolver.IResolver;
import com.s_giken.training.batch.resolver.basic.mapper.ChargeRowMapper;
import com.s_giken.training.batch.resolver.basic.mapper.MemberRowMapper;
import com.s_giken.training.batch.resolver.basic.model.BatchArgs;
import com.s_giken.training.batch.resolver.basic.model.Charge;
import com.s_giken.training.batch.resolver.basic.model.Member;

@Component
@Qualifier("basicResolver")
public class BasicResolver implements IResolver {
    final private Logger logger = LoggerFactory.getLogger(BasicResolver.class);
    final private JdbcTemplate jdbcTemplate;
    final private MemberRowMapper memberRowMapper;
    final private ChargeRowMapper chargeRowMapper;

    public BasicResolver(
            JdbcTemplate jdbcTemplate,
            MemberRowMapper memberRowMapper,
            ChargeRowMapper chargeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.memberRowMapper = memberRowMapper;
        this.chargeRowMapper = chargeRowMapper;
    }

    @Override
    public void resolve(String... args) {
        BatchArgs batchArgs;
        logger.info("-".repeat(40));
        try {
            try {
                batchArgs = parseArg(args[0]);
            } catch (Exception e) {
                logger.error("コマンドライン引数の入力値が不正です。");
                return;
            }
            execute(batchArgs);
        } finally {
            logger.info("-".repeat(40));
        }
    }

    private BatchArgs parseArg(String arg) throws DateTimeParseException {
        BatchArgs batchArgs = new BatchArgs();

        //コマンドライン引数のフォーマット変換
        DateTimeFormatter targetDateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyyMM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

        //コマンドライン引数を変換した値を代入
        batchArgs.setTargetDate(LocalDate.parse(arg, targetDateTimeFormatter));

        return batchArgs;
    }

    @Transactional
    private void execute(BatchArgs batchArgs) {
        //請求データ状況の請求年月がバッチの稼働対象か確認する
        if (existBillingData(batchArgs) == true) {
            logger.info("バッチの請求ステータスが確定済みのため、バッチを終了します。");
            return;
        }

        // 対象年月の請求情報を削除
        deleteBillingDataByTargetDate(batchArgs.getTargetDate());

        //請求ステータス追加
        insertBillingStatus(batchArgs);

        //加入者情報を抽出
        List<Member> memberList = getMemberData(batchArgs.getTargetDate());

        //料金情報を抽出
        List<Charge> chargeList = getChargeData(batchArgs.getTargetDate());

        //料金の合計額を計算
        BigDecimal sumChargeAmount = sumChargeAmount(chargeList);

        //請求データ追加
        addBillingData(memberList, sumChargeAmount, batchArgs);

        //請求明細データ追加
        addBillingDeteilData(chargeList, memberList, batchArgs);
    }

    //請求データ状況の請求年月がバッチの稼働対象か確認するメソッド
    private boolean existBillingData(BatchArgs batchArgs) {

        logger.info(batchArgs.getTargetDateForLog() + "分の請求情報を確認しています。");

        //請求データ状況の請求年月にコマンドライン引数に入力された年月の確定済みデータが存在するか
        Integer billingStatusCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM T_BILLING_STATUS WHERE billing_ym = ? AND is_commit = true",
                Integer.class, batchArgs.getTargetDate());

        if (billingStatusCount == null) {
            throw new RuntimeException(String.format("対象年月(%s): 請求ステータスを確認する際、NULLが返却されました",
                    batchArgs.getTargetDateForLog()));
        }

        return billingStatusCount.equals(1);
    }

    //加入者を抽出するメソッド
    private List<Member> getMemberData(LocalDate startTargetDate) {
        LocalDate endTargetDate = startTargetDate.plusMonths(1);
        String sql = """
                    SELECT member_id,
                           mail,
                           name,
                           address,
                           start_date,
                           end_date,
                           payment_method
                    FROM T_MEMBER
                    WHERE start_date < ? and (end_date >= ? or end_date is null)
                """;

        List<Member> memberList =
                jdbcTemplate.query(sql, memberRowMapper, endTargetDate, startTargetDate);

        return memberList;
    }

    //料金情報を抽出するメソッド
    private List<Charge> getChargeData(LocalDate startTargetDate) {
        LocalDate endTargetDate = startTargetDate.plusMonths(1);
        String sql = """
                    SELECT
                        charge_id,
                        name,
                        amount,
                        start_date,
                        end_date
                    FROM T_CHARGE
                    WHERE start_date < ? and (end_date >= ? or end_date is null)
                """;

        List<Charge> chargeList =
                jdbcTemplate.query(sql, chargeRowMapper, endTargetDate, startTargetDate);

        return chargeList;
    }

    //請求データ作成時に請求金額の合計を計算するメソッド
    private BigDecimal sumChargeAmount(List<Charge> chargeList) {
        return chargeList.stream().map(c -> c.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private void deleteBillingDataByTargetDate(LocalDate targetDate) {
        logger.info("データベースから" + targetDate.format(DateTimeFormatter.ofPattern("yyyy年MM月"))
                + "分の未確定請求情報を削除しています。");

        //請求データ状況の確定が偽なので請求データステータスと請求明細データと請求データの対象年月のデータを削除する
        jdbcTemplate.update("DELETE FROM T_BILLING_DETAIL_DATA WHERE billing_ym = ?", targetDate);
        jdbcTemplate.update("DELETE FROM T_BILLING_DATA WHERE billing_ym = ?", targetDate);
        jdbcTemplate.update("DELETE FROM T_BILLING_STATUS WHERE billing_ym = ?", targetDate);

        logger.info("削除しました。");
    }

    //請求ステータス追加のメソッド
    private void insertBillingStatus(BatchArgs batchArgs) {

        logger.info(batchArgs.getTargetDateForLog() + "分の請求ステータスを追加しています。");

        //請求ステータスにコマンドライン引数に入力された年月のデータを新規作成する
        int countCreateBillingStatus = jdbcTemplate.update(
                "INSERT INTO T_BILLING_STATUS(billing_ym,is_commit) VALUES (?, false)",
                batchArgs.getTargetDate());

        logger.info(countCreateBillingStatus + "件追加しました。");
    }

    //請求データ追加のメソッド
    private void addBillingData(
            List<Member> memberList,
            BigDecimal sumChargeAmount,
            BatchArgs batchArgs) {

        logger.info(batchArgs.getTargetDateForLog() + "分の請求データを追加しています。");

        //請求データにコマンドライン引数に入力された年月のデータを新規作成する
        String sql = """
                    INSERT INTO T_BILLING_DATA(
                        billing_ym,
                        member_id,
                        mail,
                        name,
                        address,
                        start_date,
                        end_date,
                        payment_method,
                        amount,
                        tax_ratio,total
                    ) VALUES (
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?
                    )
                """;
        long insertCount = 0L;
        for (Member member : memberList) {
            jdbcTemplate.update(
                    sql,
                    batchArgs.getTargetDate(),
                    member.getMemberId(),
                    member.getMail(),
                    member.getName(),
                    member.getAddress(),
                    member.getStartDate(),
                    member.getEndDate(),
                    member.getPaymentMethod(),
                    sumChargeAmount,
                    0.1,
                    sumChargeAmount.multiply(new BigDecimal("1.1")));
            insertCount++;
        }
        logger.info(insertCount + "件追加しました。");
    }

    //請求明細データ追加のメソッド
    private void addBillingDeteilData(
            List<Charge> chargeList,
            List<Member> memberList,
            BatchArgs batchArgs) {
        logger.info(batchArgs.getTargetDateForLog() + "分の請求明細データを追加しています。");

        //請求明細データにコマンドライン引数に入力された年月のデータを新規作成する
        String sql = """
                    INSERT INTO T_BILLING_DETAIL_DATA(
                        billing_ym,
                        member_id,
                        charge_id,
                        name,
                        amount,
                        start_date,
                        end_date
                    ) VALUES(
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?
                    )
                """;
        long insertCount = 0L;
        for (Member member : memberList) {
            for (Charge charge : chargeList) {
                jdbcTemplate.update(
                        sql,
                        batchArgs.getTargetDate(),
                        member.getMemberId(),
                        charge.getChargeId(),
                        charge.getChargeName(),
                        charge.getAmount(),
                        charge.getStartDate(),
                        charge.getEndDate());
                insertCount++;
            }
        }
        logger.info(insertCount + "件追加しました。");
    }

}
