package com.s_giken.training.webapp.model.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import com.s_giken.training.webapp.model.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 加入者情報エンティティ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    /**
     * 加入者情報ID
     */
    @Nullable
    private Long memberId;

    /**
     * メールアドレス
     */
    @NotBlank
    @Size(max = 255)
    private String mail;

    /**
     * 利用者氏名
     */
    @NotBlank
    @Size(max = 31)
    private String name;

    /**
     * 利用者住所
     */
    @NotBlank
    @Size(max = 127)
    private String address;

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
     * 支払い方法
     */
    @NotNull
    private PaymentMethod paymentMethod;

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
