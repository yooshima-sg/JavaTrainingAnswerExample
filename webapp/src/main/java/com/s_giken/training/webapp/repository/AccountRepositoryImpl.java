package com.s_giken.training.webapp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.s_giken.training.webapp.model.entity.Account;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Account> rowMapper;

    @Override
    public Optional<Account> findUserByUsername(String username) {
        String sql = "SELECt * FROM t_ACCOUNT WHERE username = ?";
        List<Account> users = jdbcTemplate.query(sql, rowMapper, username);
        if (users.size() == 1) {
            return Optional.of(users.get(0));
        } else {
            return Optional.empty();
        }
    }
}
