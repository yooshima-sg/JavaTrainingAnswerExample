package com.s_giken.training.webapp.repository;

import java.util.Optional;
import com.s_giken.training.webapp.model.entity.Account;

public interface AccountRepository {
    public Optional<Account> findUserByUsername(String username);
}
