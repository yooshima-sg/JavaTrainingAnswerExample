package com.s_giken.training.webapp.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.s_giken.training.webapp.model.entity.Account;
import com.s_giken.training.webapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findUserByUsername(username);
        if (!account.isPresent()) {
            throw new UsernameNotFoundException("ユーザ " + username + " が見つかりません。");
        }

        return User.builder()
                .username(account.get().getUsername())
                .password(account.get().getPassword())
                .roles(account.get().getRole())
                .build();
    }
}
