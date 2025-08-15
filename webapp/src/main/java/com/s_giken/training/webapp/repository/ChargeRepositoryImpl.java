package com.s_giken.training.webapp.repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;
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

	@Override
	public List<Charge> findAll() {
		String sql = "SELECT * FROM T_CHARGE ORDER BY id";
		List<Charge> result = jdbcTemplate.query(sql, rowMapper);
		return result;
	}

	@Override
	public Optional<Charge> findById(Long id) {
		String sql = "SELECT * FROM T_CHARGE WHERE charge_id =  ? ";
		Object[] args = {id};
		int[] argTypes = {Types.BIGINT};
		Charge charge = jdbcTemplate.queryForObject(sql, args, argTypes, rowMapper);
		return Optional.ofNullable(charge);
	}

	@Override
	public List<Charge> findByChargeNameLike(String chargeName) {

		String sql = "SELECT * FROM T_CHARGE WHERE name like ?";
		Object[] p = {"%" + chargeName + "%"};
		int[] pTypes = {Types.VARCHAR};

		List<Charge> result = jdbcTemplate.query(sql, p, pTypes, rowMapper);

		return result;
	}

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

	@Override
	public int deleteById(Long id) {
		String sql = "DELETE FROM T_CHARGE WHERE charge_id = ?";

		int processed_count = jdbcTemplate.update(sql, id);

		return processed_count;
	}

}
