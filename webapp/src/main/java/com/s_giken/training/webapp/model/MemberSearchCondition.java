package com.s_giken.training.webapp.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 加入者情報の検索条件を格納するクラス
 */
@Data // メンバー変数に対するゲッター・セッターを自動生成
@NoArgsConstructor // 引数のないコンストラクタを自動生成
@AllArgsConstructor // 全てのメンバ変数に対する引数を持つコンストラクタを自動生成
public class MemberSearchCondition {
    /**
     * メールアドレスの一部
     */
    private String mail;
    /**
     * 利用者氏名の一部
     */
    private String name;

    /**
     * ソート対象項目名
     */
    @Pattern(regexp = "^(member_id|mail|name|address|start_date|end_date|payment_method)$")
    private String sortColName;

    /**
     * ソート方法
     */
    @Pattern(regexp = "^(asc|desc)$")
    private String sortOrder;
}
