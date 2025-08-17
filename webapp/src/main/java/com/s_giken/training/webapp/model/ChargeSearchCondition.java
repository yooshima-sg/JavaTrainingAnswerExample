package com.s_giken.training.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeSearchCondition {
    // 料金名の一部
    private String name;

    // ソート対象項目名
    private String sortColName = "";// 初期化
    // ソート方法
    private String sortOrder = "";
}
