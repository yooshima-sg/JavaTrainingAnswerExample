package com.s_giken.training.batch.resolver.basic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ChargeTests {

  @Test
  void testDefaultConstructor() {
    Charge charge = new Charge();

    assertNotNull(charge);
    assertNull(charge.getChargeId());
    assertNull(charge.getChargeName());
    assertNull(charge.getAmount());
    assertNull(charge.getStartDate());
    assertNull(charge.getEndDate());
  }

  @Test
  void testAllArgsConstructor() {
    Long chargeId = 1L;
    String chargeName = "Basic Plan";
    BigDecimal amount = new BigDecimal("1000");
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);

    Charge charge = new Charge(chargeId, chargeName, amount, startDate, endDate);

    assertEquals(chargeId, charge.getChargeId());
    assertEquals(chargeName, charge.getChargeName());
    assertEquals(amount, charge.getAmount());
    assertEquals(startDate, charge.getStartDate());
    assertEquals(endDate, charge.getEndDate());
  }

  @Test
  void testGettersAndSetters() {
    Charge charge = new Charge();

    charge.setChargeId(2L);
    charge.setChargeName("Premium Plan");
    charge.setAmount(new BigDecimal("2500"));
    charge.setStartDate(LocalDate.of(2025, 6, 15));
    charge.setEndDate(LocalDate.of(2026, 6, 14));

    assertEquals(2L, charge.getChargeId());
    assertEquals("Premium Plan", charge.getChargeName());
    assertEquals(new BigDecimal("2500"), charge.getAmount());
    assertEquals(LocalDate.of(2025, 6, 15), charge.getStartDate());
    assertEquals(LocalDate.of(2026, 6, 14), charge.getEndDate());
  }

  @Test
  void testWithNullEndDate() {
    Charge charge = new Charge();
    charge.setChargeId(3L);
    charge.setChargeName("永続プラン");
    charge.setAmount(new BigDecimal("5000"));
    charge.setStartDate(LocalDate.of(2025, 1, 1));
    charge.setEndDate(null);

    assertNull(charge.getEndDate());
    assertEquals(3L, charge.getChargeId());
    assertEquals("永続プラン", charge.getChargeName());
  }

  @Test
  void testWithZeroAmount() {
    Charge charge = new Charge();
    charge.setAmount(BigDecimal.ZERO);

    assertEquals(BigDecimal.ZERO, charge.getAmount());
  }

  @Test
  void testWithNegativeAmount() {
    Charge charge = new Charge();
    BigDecimal negativeAmount = new BigDecimal("-100");
    charge.setAmount(negativeAmount);

    assertEquals(negativeAmount, charge.getAmount());
  }

  @Test
  void testLargeAmount() {
    Charge charge = new Charge();
    BigDecimal largeAmount = new BigDecimal("999999999");
    charge.setAmount(largeAmount);

    assertEquals(largeAmount, charge.getAmount());
  }

  @Test
  void testEmptyChargeName() {
    Charge charge = new Charge();
    charge.setChargeName("");

    assertEquals("", charge.getChargeName());
  }
}
