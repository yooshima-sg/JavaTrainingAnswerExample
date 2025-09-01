package com.s_giken.training.webapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.s_giken.training.webapp.model.entity.Account;

/**
 * データベースからのT_USERデータをMemberオブジェクトにマッピングする
 * 
 * @Autowired で注入できるように、DIコンテナのコンポーネントとする。
 */
@Component
public class AccountRowMapper implements RowMapper<Account> {
    /**
     * マッピング処理を行うメソッド
     * 
     * @param rs データベースからのレコードセット
     * @param rowNum 処理行数
     * 
     * @return Memberオブジェクト
     */
    @Override
    public Account mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();

        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setRole(rs.getString("role"));
        account.setCreatedAt(rs.getTimestamp("created_at"));
        account.setModifiedAt(rs.getTimestamp("modified_at"));

        return account;
    }
}
