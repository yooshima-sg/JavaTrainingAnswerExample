package com.s_giken.training.webapp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.s_giken.training.webapp.exception.TooManyResultException;
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

        return switch (users.size()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(users.get(0));
            default -> throw new TooManyResultException("Too many records.");
        };
    }
}
