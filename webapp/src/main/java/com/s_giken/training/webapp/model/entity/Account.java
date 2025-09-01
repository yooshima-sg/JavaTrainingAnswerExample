package com.s_giken.training.webapp.model.entity;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String username;
    private String password;
    private String role;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
