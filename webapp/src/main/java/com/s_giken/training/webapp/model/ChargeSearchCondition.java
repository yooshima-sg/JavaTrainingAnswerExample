package com.s_giken.training.webapp.model;

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
     * ソート対象列名
     */
    private String sortColName = "";
    /**
     * ソート方法
     */
    private String sortOrder = "";
}
