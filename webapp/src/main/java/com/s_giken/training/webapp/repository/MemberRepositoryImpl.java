package com.s_giken.training.webapp.repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.s_giken.training.webapp.exception.TooManyResultException;
import com.s_giken.training.webapp.model.entity.Member;
import lombok.RequiredArgsConstructor;

/**
 * 加入者情報 リポジトリクラス
 */
@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> rowMapper;

    /**
     * 加入者情報をすべて取得する。
     * 
     * @return Memberオブジェクトのリスト
     */
    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM T_MEMBER";
        List<Member> result = jdbcTemplate.query(sql, rowMapper);
        return result;
    }

    /**
     * 指定した加入者IDの加入者情報を取得する。
     * 
     * @param id 加入者ID
     * @return Optional型の Memberオブジェクト
     */
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM T_MEMBER WHERE member_id = ?";

        List<Member> members = jdbcTemplate.query(sql, rowMapper, id);

        return switch (members.size()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(members.get(0));
            default -> throw new TooManyResultException("Too many records.");
        };
    }

    /**
     * メールアドレスと名前の部分一致で加入者情報を取得する
     * 
     * @param mail メールアドレスの一部
     * @param name 名前の一部
     * @param sortColName ソートしたい列名
     * @param sortOrder ソート方法(昇順or降順)
     *
     * @return Memberオブジェクトのリスト
     */
    @Override
    public List<Member> findByMailAndNameLikeWithSort(
            String mail,
            String name,
            String sortColName,
            String sortOrder) {

        if (!ALLOW_COLUMN_NAMES.contains(sortColName)) {
            throw new IllegalArgumentException("sortColName is Invalid.");
        }

        if (!ALLOW_SORT_ORDER.contains(sortOrder)) {
            throw new IllegalArgumentException("sortOrder is invalid");
        }
        String sql =
                "SELECT * FROM T_MEMBER WHERE mail like ? AND name like ? ORDER BY @@sortColName@@ @@sortOrder@@";
        sql = sql.replace("@@sortColName@@", sortColName.toUpperCase())
                .replace("@@sortOrder@@", sortOrder.toUpperCase());
        Object[] args = {"%" + mail + "%", "%" + name + "%"};
        int[] argTypes = {Types.VARCHAR, Types.VARCHAR};
        List<Member> result = jdbcTemplate.query(sql, args, argTypes, rowMapper);
        return result;
    }

    /**
     * 加入者情報をデータベースへ登録する。
     * 
     * @param member 追加するMemberオブジェクト。 memberIdプロパティの値は null としなくてはならない
     * @return 処理した件数
     */
    @Override
    public int add(Member member) {
        Long memberId = member.getMemberId();
        if (memberId == null) {
            memberId =
                    jdbcTemplate.queryForObject("SELECT NEXT VALUE FOR t_member_seq", Long.class);
            member.setMemberId(memberId);
        }

        String sql = """
                    INSERT INTO T_MEMBER (
                        member_id,
                        mail,
                        name,
                        address,
                        start_date,
                        end_date,
                        payment_method,
                        created_at,
                        modified_at)
                    VALUES (
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        CURRENT_TIMESTAMP,
                        CURRENT_TIMESTAMP)
                """;
        int processed_count = jdbcTemplate.update(
                sql,
                memberId,
                member.getMail(),
                member.getName(),
                member.getAddress(),
                member.getStartDate(),
                member.getEndDate(),
                member.getPaymentMethod().getCode());

        return processed_count;
    }

    /**
     * データベースの加入者情報を更新する。
     * 
     * @param member 更新するMemberオブジェクト。 memberIdプロパティには値が設定されている必要がある。
     * @return 処理した件数
     */
    @Override
    public int update(Member member) {
        String sql = """
                    UPDATE T_MEMBER
                    SET
                        mail = ?,
                        name = ?,
                        address = ?,
                        start_date = ?,
                        end_date = ?,
                        payment_method = ?,
                        modified_at = CURRENT_TIMESTAMP
                    WHERE member_id = ?
                """;
        int processed_count = jdbcTemplate.update(
                sql,
                member.getMail(),
                member.getName(),
                member.getAddress(),
                member.getStartDate(),
                member.getEndDate(),
                member.getPaymentMethod().getCode(),
                member.getMemberId());

        return processed_count;
    }

    /**
     * データベースから指定した加入者IDの加入者情報を削除する。
     * 
     * @param id 加入者ID
     * @return 処理した件数
     */
    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM T_MEMBER WHERE member_id = ?";

        int processed_count = jdbcTemplate.update(sql, id);

        return processed_count;
    }
}
