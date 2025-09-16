package com.s_giken.training.webapp.model.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 料金情報エンティティ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Charge {

	@Nullable
	private Long chargeId;

	@NotBlank
	@Size(max = 127)
	private String chargeName;

	@NotNull
	@DecimalMax("999999999")
	@DecimalMin("0")
	private BigDecimal amount;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nullable
	private LocalDate endDate;

	@Nullable
	private Timestamp createdAt;

	@Nullable
	private Timestamp modifiedAt;

}
