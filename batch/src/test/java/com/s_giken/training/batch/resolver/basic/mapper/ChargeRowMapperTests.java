package com.s_giken.training.batch.resolver.basic.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.s_giken.training.batch.resolver.basic.model.Charge;

@ExtendWith(MockitoExtension.class)
class ChargeRowMapperTests {

  @Mock
  private ResultSet mockResultSet;

  private ChargeRowMapper chargeRowMapper;

  @BeforeEach
  void setUp() {
    chargeRowMapper = new ChargeRowMapper();
  }

  @Test
  void testMapRowWithAllFields() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(1L);
    when(mockResultSet.getString("name")).thenReturn("Basic Plan");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("1000"));
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2025-01-01"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2025-12-31"));

    Charge result = chargeRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(1L, result.getChargeId());
    assertEquals("Basic Plan", result.getChargeName());
    assertEquals(new BigDecimal("1000"), result.getAmount());
    assertEquals(LocalDate.of(2025, 1, 1), result.getStartDate());
    assertEquals(LocalDate.of(2025, 12, 31), result.getEndDate());
  }

  @Test
  void testMapRowWithNullEndDate() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(2L);
    when(mockResultSet.getString("name")).thenReturn("Premium Plan");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("2500"));
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2025-06-15"));
    when(mockResultSet.getDate("end_date")).thenReturn(null);

    Charge result = chargeRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(2L, result.getChargeId());
    assertEquals("Premium Plan", result.getChargeName());
    assertEquals(new BigDecimal("2500"), result.getAmount());
    assertEquals(LocalDate.of(2025, 6, 15), result.getStartDate());
    assertNull(result.getEndDate());
  }

  @Test
  void testMapRowWithNullStartDate() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(3L);
    when(mockResultSet.getString("name")).thenReturn("Ultimate Plan");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("5000"));
    when(mockResultSet.getDate("start_date")).thenReturn(null);
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2025-12-31"));

    Charge result = chargeRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(3L, result.getChargeId());
    assertEquals("Ultimate Plan", result.getChargeName());
    assertEquals(new BigDecimal("5000"), result.getAmount());
    assertNull(result.getStartDate());
    assertEquals(LocalDate.of(2025, 12, 31), result.getEndDate());
  }

  @Test
  void testMapRowWithBothDatesNull() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(4L);
    when(mockResultSet.getString("name")).thenReturn("Forever Plan");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("999.99"));
    when(mockResultSet.getDate("start_date")).thenReturn(null);
    when(mockResultSet.getDate("end_date")).thenReturn(null);

    Charge result = chargeRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(4L, result.getChargeId());
    assertEquals("Forever Plan", result.getChargeName());
    assertEquals(new BigDecimal("999.99"), result.getAmount());
    assertNull(result.getStartDate());
    assertNull(result.getEndDate());
  }

  @Test
  void testMapRowWithZeroAmount() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(5L);
    when(mockResultSet.getString("name")).thenReturn("Free Plan");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(BigDecimal.ZERO);
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2025-01-01"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2025-12-31"));

    Charge result = chargeRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(5L, result.getChargeId());
    assertEquals("Free Plan", result.getChargeName());
    assertEquals(BigDecimal.ZERO, result.getAmount());
    assertEquals(LocalDate.of(2025, 1, 1), result.getStartDate());
    assertEquals(LocalDate.of(2025, 12, 31), result.getEndDate());
  }

  @Test
  void testMapRowWithNegativeAmount() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(6L);
    when(mockResultSet.getString("name")).thenReturn("Discount Plan");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("-100"));
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2025-03-15"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2025-09-15"));

    Charge result = chargeRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(6L, result.getChargeId());
    assertEquals("Discount Plan", result.getChargeName());
    assertEquals(new BigDecimal("-100"), result.getAmount());
    assertEquals(LocalDate.of(2025, 3, 15), result.getStartDate());
    assertEquals(LocalDate.of(2025, 9, 15), result.getEndDate());
  }

  @Test
  void testMapRowWithDifferentRowNumbers() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(7L);
    when(mockResultSet.getString("name")).thenReturn("Standard Plan");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("1500"));
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2025-07-01"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2024-06-30"));

    Charge result1 = chargeRowMapper.mapRow(mockResultSet, 0);
    Charge result2 = chargeRowMapper.mapRow(mockResultSet, 50);

    assertNotNull(result1);
    assertNotNull(result2);
    assertEquals(result1.getChargeId(), result2.getChargeId());
    assertEquals(result1.getChargeName(), result2.getChargeName());
    assertEquals(result1.getAmount(), result2.getAmount());
  }

  @Test
  void testMapRowWithEmptyName() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(8L);
    when(mockResultSet.getString("name")).thenReturn("");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("1"));
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2025-01-01"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2025-12-31"));

    Charge result = chargeRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(8L, result.getChargeId());
    assertEquals("", result.getChargeName());
    assertEquals(new BigDecimal("1"), result.getAmount());
    assertEquals(LocalDate.of(2025, 1, 1), result.getStartDate());
    assertEquals(LocalDate.of(2025, 12, 31), result.getEndDate());
  }

  @Test
  void testMapRowWithLargeAmount() throws SQLException {
    when(mockResultSet.getLong("charge_id")).thenReturn(9L);
    when(mockResultSet.getString("name")).thenReturn("Enterprise Plan");
    when(mockResultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("999999999"));
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2025-01-01"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2025-12-31"));

    Charge result = chargeRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(9L, result.getChargeId());
    assertEquals("Enterprise Plan", result.getChargeName());
    assertEquals(new BigDecimal("999999999"), result.getAmount());
    assertEquals(LocalDate.of(2025, 1, 1), result.getStartDate());
    assertEquals(LocalDate.of(2025, 12, 31), result.getEndDate());
  }
}
