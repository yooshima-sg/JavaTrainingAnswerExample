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
    private String name;

    private String sortColName = "";// 初期化
    private String sortOrder = "";
}
