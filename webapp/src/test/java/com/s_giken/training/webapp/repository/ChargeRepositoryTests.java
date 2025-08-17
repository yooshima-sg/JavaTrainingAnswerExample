package com.s_giken.training.webapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;

import com.s_giken.training.webapp.model.entity.Charge;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ChargeRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ChargeRepository chargeRepository;

  private Charge testCharge;

  @BeforeEach
  void setUp() {
    testCharge = new Charge();
    testCharge.setName("テスト料金");
    testCharge.setAmount(new BigDecimal("1000"));
    testCharge.setStartDate(new Date());
  }

  @Test
  void save_正常系() {
    Charge savedCharge = chargeRepository.save(testCharge);

    assertTrue(savedCharge.getChargeId() != 0L);
    assertEquals("テスト料金", savedCharge.getName());
    assertEquals(new BigDecimal("1000"), savedCharge.getAmount());
  }

  @Test
  void findById_正常系() {
    testCharge = entityManager.persistAndFlush(testCharge);

    Optional<Charge> result = chargeRepository.findById(testCharge.getChargeId());

    assertTrue(result.isPresent());
    assertEquals("テスト料金", result.get().getName());
  }

  @Test
  void findAll_正常系() {
    entityManager.persistAndFlush(testCharge);

    List<Charge> charges = chargeRepository.findAll();

    assertFalse(charges.isEmpty());
    assertTrue(charges.stream().anyMatch(c -> "テスト料金".equals(c.getName())));
  }

  @Test
  void findByNameLike_正常系() {
    entityManager.persistAndFlush(testCharge);

    List<Charge> result = chargeRepository.findByNameLike("%テスト%");

    assertFalse(result.isEmpty());
    assertEquals("テスト料金", result.get(0).getName());
  }

  @Test
  void findByNameLike_正常系_ソート指定() {
    Charge charge2 = new Charge();
    charge2.setName("あいうえお料金");
    charge2.setAmount(new BigDecimal("2000"));
    charge2.setStartDate(new Date());

    entityManager.persistAndFlush(testCharge);
    entityManager.persistAndFlush(charge2);

    List<Charge> result = chargeRepository.findByNameLike(
        "%料金%", Sort.by(Sort.Direction.ASC, "name"));

    assertEquals(2, result.size());
    assertEquals("あいうえお料金", result.get(0).getName());
    assertEquals("テスト料金", result.get(1).getName());
  }

  @Test
  void deleteById_正常系() {
    testCharge = entityManager.persistAndFlush(testCharge);
    Long chargeId = testCharge.getChargeId();

    chargeRepository.deleteById(chargeId);

    Optional<Charge> result = chargeRepository.findById(chargeId);
    assertFalse(result.isPresent());
  }
}
