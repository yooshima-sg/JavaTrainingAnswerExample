package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;
import com.s_giken.training.webapp.model.ChargeSearchCondition;
import com.s_giken.training.webapp.model.entity.Charge;


/**
 * 加入者情報ビジネスロジック(サービス)クラス
 */
public interface ChargeService {
    public List<Charge> findAll();

    public Optional<Charge> findById(Long chargeId);

    public List<Charge> findByConditions(ChargeSearchCondition chargeSearchCondition);

    public void add(Charge charge);

    public void update(Charge charge);

    public void deleteById(Long chargeId);

}
