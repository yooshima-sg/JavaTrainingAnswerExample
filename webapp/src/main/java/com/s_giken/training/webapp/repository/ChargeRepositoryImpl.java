package com.s_giken.training.webapp.repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.s_giken.training.webapp.model.entity.Charge;

@Repository
public class ChargeRepositoryImpl implements ChargeRepository {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Charge> rowMapper;

	public ChargeRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<Charge> rowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.rowMapper = rowMapper;
	}

	/**
	 * 料金情報をすべて取得する
	 * 
	 * @return 料金情報のリスト
	 */
	@Override
	public List<Charge> findAll() {
		String sql = "SELECT * FROM T_CHARGE ORDER BY id";
		List<Charge> result = jdbcTemplate.query(sql, rowMapper);
		return result;
	}

	/**
	 * 料金IDに一致する料金情報を取得する
	 * 
	 * @param id 料金ID
	 * 
	 * @return 料金情報のOptional型
	 */
	@Override
	public Optional<Charge> findById(Long id) {
		String sql = "SELECT * FROM T_CHARGE WHERE charge_id =  ? ";
		Object[] args = {id};
		int[] argTypes = {Types.BIGINT};

		Charge charge;
		try {
			charge = jdbcTemplate.queryForObject(sql, args, argTypes, rowMapper);
		} catch (DataAccessException e) {
			charge = null;
		}

		return Optional.ofNullable(charge);
	}

	/**
	 * 料金名の一部から料金情報を取得する
	 * 
	 * @param chargeName 料金名の一部
	 * 
	 * @return 料金情報のリスト
	 */
	@Override
	public List<Charge> findByChargeNameLike(String chargeName) {

		String sql = "SELECT * FROM T_CHARGE WHERE name like ?";
		Object[] p = {"%" + chargeName + "%"};
		int[] pTypes = {Types.VARCHAR};

		List<Charge> result = jdbcTemplate.query(sql, p, pTypes, rowMapper);

		return result;
	}

	/**
	 * 料金情報を追加する
	 * 
	 * @param charge 料金情報エンティティオブジェクト
	 * 
	 * @returnt 追加した料金情報の数
	 */
	@Override
	public int add(Charge charge) {
		Long chargeId = charge.getChargeId();
		if (chargeId == null) {
			chargeId = jdbcTemplate.queryForObject(
					"SELECT NEXT VALUE FOR t_member_seq",
					Long.class);
			charge.setChargeId(chargeId);
		}

		String sql = """
				INSERT INTO T_CHARGE (
					charge_id,
					name,
					amount,
					start_date,
					end_date,
					created_at,
					modified_at)
				VALUES (
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
				chargeId,
				charge.getChargeName(),
				charge.getAmount(),
				charge.getStartDate(),
				charge.getEndDate());

		return processed_count;
	}

	/**
	 * 料金情報を更新する
	 * 
	 * @param charge 更新する情報が格納された料金情報エンティティオブジェクト
	 * 
	 * @return 更新した料金情報の数
	 */
	@Override
	public int update(Charge charge) {
		String sql = """
				    UPDATE T_CHARGE
				    SET
				        name = ?,
				        amount = ?,
				        start_date = ?,
				        end_date = ?,
				        modified_at = CURRENT_TIMESTAMP
				    WHERE charge_id = ?
				""";
		int processed_count = jdbcTemplate.update(
				sql,
				charge.getChargeName(),
				charge.getAmount(),
				charge.getStartDate(),
				charge.getEndDate(),
				charge.getChargeId());

		return processed_count;
	}

	/**
	 * 料金IDを持つ料金情報を削除する
	 * 
	 * @param id 料金情報ID
	 * 
	 * @return 削除した料金情報の数
	 */
	@Override
	public int deleteById(Long id) {
		String sql = "DELETE FROM T_CHARGE WHERE charge_id = ?";

		int processed_count = jdbcTemplate.update(sql, id);

		return processed_count;
	}

}
