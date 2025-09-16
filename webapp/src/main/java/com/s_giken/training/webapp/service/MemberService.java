package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;
import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.model.MemberSearchCondition;

/*
 * 【ヒント】
 * MemberService をインターフェースとして定義することで、
 * インターフェースを実体化するクラスができることを明確化する。
 */

/**
 * 加入者情報ビジネスロジック(サービス)インターフェース
 */
public interface MemberService {
    /**
     * 加入者情報をすべて取得する
     * 
     * @return 取得した加入者情報のリスト
     */
    public List<Member> findAll();

    /**
     * 指定した加入者IDの加入者情報を取得する
     * 
     * @param memberId 取得したい加入者情報の加入者ID
     * @return 加入者情報のOptional型
     */
    public Optional<Member> findById(Long memberId);

    /**
     * 検索条件による加入者情報の検索結果を取得する
     * 
     * @param memberSearchCondition 検索条件
     * @return 取得した加入者情報のリスト
     */
    public List<Member> findByConditions(MemberSearchCondition memberSearchCondition);

    /**
     * 加入者情報を追加登録する
     * 
     * @param member 追加登録したい加入者情報
     */
    public void add(Member member);

    /**
     * 加入者情報を更新する
     * 
     * @param member 更新する加入者情報
     */
    public void update(Member member);

    /**
     * 加入者情報を削除する
     * 
     * @param memberId 削除対象となる加入者情報の加入者ID
     */
    public void deleteById(Long memberId);
}
