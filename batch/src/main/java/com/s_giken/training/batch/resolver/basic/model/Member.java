package com.s_giken.training.batch.resolver.basic.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long memberId;
    private String mail;
    private String name;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private Byte paymentMethod;
}
