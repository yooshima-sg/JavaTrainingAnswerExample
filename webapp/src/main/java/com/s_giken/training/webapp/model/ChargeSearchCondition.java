package com.s_giken.training.webapp.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 料金情報の検索条件を格納するクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeSearchCondition {
    /**
     * 料金名の一部
     */
    private String name;

    /**
     * ソート対象項目
     */
    @Pattern(regexp = "^(charge_id|name|amount|start_date|end_date)$")
    private String sortColName;

    /**
     * ソート方法
     */
    @Pattern(regexp = "^(asc|desc)$")
    private String sortOrder;
}
