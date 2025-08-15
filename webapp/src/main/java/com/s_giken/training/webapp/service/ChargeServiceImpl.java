
package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.s_giken.training.webapp.exception.AttributeErrorException;
import com.s_giken.training.webapp.model.ChargeSearchCondition;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.repository.ChargeRepository;

@Service
public class ChargeServiceImpl implements ChargeService {
    private ChargeRepository chargeRepository;

    public ChargeServiceImpl(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    @Override
    public List<Charge> findAll() {
        return chargeRepository.findAll();
    }

    @Override
    public Optional<Charge> findById(Long chargeId) {
        return chargeRepository.findById(chargeId);
    }

    @Override
    public List<Charge> findByConditions(ChargeSearchCondition chargeSearchCondition) {
        return chargeRepository.findByChargeNameLike(chargeSearchCondition.getName());
    }

    @Override
    public void add(Charge charge) {
        if (charge.getChargeId() != null) {
            throw new AttributeErrorException("料金IDが指定されていると登録できません。");
        }
        chargeRepository.add(charge);
    }

    @Override
    public void update(Charge charge) {
        if (charge.getChargeId() == null) {
            throw new AttributeErrorException("料金IDが指定されていません。");
        }
        chargeRepository.update(charge);
    }

    @Override
    public void deleteById(Long chargeId) {
        chargeRepository.deleteById(chargeId);
    }
}
