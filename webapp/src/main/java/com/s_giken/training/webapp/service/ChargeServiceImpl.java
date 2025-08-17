
package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.s_giken.training.webapp.exception.AttributeErrorException;
import com.s_giken.training.webapp.model.ChargeSearchCondition;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.repository.ChargeRepository;

/**
 * 料金情報ビジネスロジック(サービス)クラス
 */
@Service
public class ChargeServiceImpl implements ChargeService {
    private ChargeRepository chargeRepository;

    public ChargeServiceImpl(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    /**
     * 料金情報をすべて取得する
     * 
     * @return 料金情報のリスト
     */
    @Override
    public List<Charge> findAll() {
        return chargeRepository.findAll();
    }

    /**
     * 料金IDに一致する料金情報を取得する
     * 
     * @param id 料金ID
     * 
     * @return 料金情報のOptional型
     */
    @Override
    public Optional<Charge> findById(Long chargeId) {
        return chargeRepository.findById(chargeId);
    }

    /**
     * 検索条件に一致する料金情報を取得する
     * 
     * @param chargeSearchCondition 料金情報検索用オブジェクト
     * 
     * @return 料金情報のリスト
     */
    @Override
    public List<Charge> findByConditions(ChargeSearchCondition chargeSearchCondition) {
        return chargeRepository.findByChargeNameLike(chargeSearchCondition.getName());
    }

    /**
     * 料金情報を追加する
     * 
     * @param charge 料金情報エンティティオブジェクト
     * 
     * @returnt 追加した料金情報の数
     */
    @Override
    public void add(Charge charge) {
        if (charge.getChargeId() != null) {
            throw new AttributeErrorException("料金IDが指定されていると登録できません。");
        }
        chargeRepository.add(charge);
    }

    /**
     * 料金情報を更新する
     * 
     * @param charge 更新する情報が格納された料金情報エンティティオブジェクト
     * 
     * @return 更新した料金情報の数
     */
    @Override
    public void update(Charge charge) {
        if (charge.getChargeId() == null) {
            throw new AttributeErrorException("料金IDが指定されていません。");
        }
        chargeRepository.update(charge);
    }

    /**
     * 料金IDを持つ料金情報を削除する
     * 
     * @param id 料金情報ID
     * 
     * @return 削除した料金情報の数
     */
    @Override
    public void deleteById(Long chargeId) {
        chargeRepository.deleteById(chargeId);
    }
}
