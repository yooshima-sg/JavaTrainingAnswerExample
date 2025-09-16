package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;
import com.s_giken.training.webapp.model.ChargeSearchCondition;
import com.s_giken.training.webapp.model.entity.Charge;


/**
 * 料金情報ビジネスロジック(サービス)クラス
 */
public interface ChargeService {
    /**
     * すべての料金情報を取得する
     * 
     * @return 取得した料金情報のリスト
     */
    public List<Charge> findAll();

    /**
     * 指定した料金IDの料金情報を取得する
     * 
     * @param chargeId 取得する料金情報の料金ID
     * @return 料金情報のOPtional型
     */
    public Optional<Charge> findById(Long chargeId);

    /**
     * 条件によ料金情報の検索結果を取得する
     * 
     * @param chargeSearchCondition 検索条件
     * @return 取得した料金情報のリスト
     */
    public List<Charge> findByConditions(ChargeSearchCondition chargeSearchCondition);

    /**
     * 料金情報を追加登録する
     * 
     * @param charge 追加登録する料金情報
     */
    public void add(Charge charge);

    /**
     * 料金情報を更新する
     * 
     * @param charge 更新する他の料金情報
     */
    public void update(Charge charge);

    /**
     * 料金情報を削除する
     * 
     * @param chargeId 削除対象とする料金情報ID
     * 
     */
    public void deleteById(Long chargeId);

}
