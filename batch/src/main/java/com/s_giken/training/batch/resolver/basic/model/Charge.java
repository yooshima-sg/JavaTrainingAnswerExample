package com.s_giken.training.batch.resolver.basic.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Charge {
    private Long chargeId;
    private String chargeName;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;
}
