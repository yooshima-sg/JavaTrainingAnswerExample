package com.s_giken.training.webapp.repository;

import java.util.List;
import java.util.Optional;
import com.s_giken.training.webapp.model.entity.Charge;

public interface ChargeRepository {

	public List<Charge> findAll();

	public Optional<Charge> findById(Long Id);

	public List<Charge> findByChargeNameLike(String chargeName);

	public int add(Charge charge);

	public int update(Charge cahrge);

	public int deleteById(Long id);
}
