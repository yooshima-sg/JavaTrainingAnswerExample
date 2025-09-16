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
	/**
	 * 料金情報ID
	 */
	@Nullable
	private Long chargeId;

	/**
	 * 料金名
	 */
	@NotBlank
	@Size(max = 127)
	private String chargeName;

	/**
	 * 料金
	 */
	@NotNull
	@DecimalMax("999999999")
	@DecimalMin("0")
	private BigDecimal amount;

	/**
	 * 適用開始日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate startDate;

	/**
	 * 適用終了日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nullable
	private LocalDate endDate;

	/**
	 * 情報作成日時
	 */
	@Nullable
	private Timestamp createdAt;

	/**
	 * 情報更新日時
	 */
	@Nullable
	private Timestamp modifiedAt;

}
