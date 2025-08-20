package com.s_giken.training.batch.resolver.basic.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.s_giken.training.batch.resolver.basic.model.Member;

@ExtendWith(MockitoExtension.class)
class MemberRowMapperTests {

  @Mock
  private ResultSet mockResultSet;

  private MemberRowMapper memberRowMapper;

  @BeforeEach
  void setUp() {
    memberRowMapper = new MemberRowMapper();
  }

  @Test
  void testMapRowWithAllFields() throws SQLException {
    when(mockResultSet.getLong("member_id")).thenReturn(1L);
    when(mockResultSet.getString("mail")).thenReturn("test@example.com");
    when(mockResultSet.getString("name")).thenReturn("Test User");
    when(mockResultSet.getString("address")).thenReturn("Test Address");
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2023-01-01"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2023-12-31"));
    when(mockResultSet.getByte("payment_method")).thenReturn((byte) 1);

    Member result = memberRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(1L, result.getMemberId());
    assertEquals("test@example.com", result.getMail());
    assertEquals("Test User", result.getName());
    assertEquals("Test Address", result.getAddress());
    assertEquals(LocalDate.of(2023, 1, 1), result.getStartDate());
    assertEquals(LocalDate.of(2023, 12, 31), result.getEndDate());
    assertEquals((byte) 1, result.getPaymentMethod());
  }

  @Test
  void testMapRowWithNullEndDate() throws SQLException {
    when(mockResultSet.getLong("member_id")).thenReturn(2L);
    when(mockResultSet.getString("mail")).thenReturn("active@example.com");
    when(mockResultSet.getString("name")).thenReturn("Active User");
    when(mockResultSet.getString("address")).thenReturn("Active Address");
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2023-06-15"));
    when(mockResultSet.getDate("end_date")).thenReturn(null);
    when(mockResultSet.getByte("payment_method")).thenReturn((byte) 2);

    Member result = memberRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(2L, result.getMemberId());
    assertEquals("active@example.com", result.getMail());
    assertEquals("Active User", result.getName());
    assertEquals("Active Address", result.getAddress());
    assertEquals(LocalDate.of(2023, 6, 15), result.getStartDate());
    assertNull(result.getEndDate());
    assertEquals((byte) 2, result.getPaymentMethod());
  }

  @Test
  void testMapRowWithNullStartDate() throws SQLException {
    when(mockResultSet.getLong("member_id")).thenReturn(3L);
    when(mockResultSet.getString("mail")).thenReturn("user@test.com");
    when(mockResultSet.getString("name")).thenReturn("User Name");
    when(mockResultSet.getString("address")).thenReturn("User Address");
    when(mockResultSet.getDate("start_date")).thenReturn(null);
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2023-12-31"));
    when(mockResultSet.getByte("payment_method")).thenReturn((byte) 0);

    Member result = memberRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(3L, result.getMemberId());
    assertEquals("user@test.com", result.getMail());
    assertEquals("User Name", result.getName());
    assertEquals("User Address", result.getAddress());
    assertNull(result.getStartDate());
    assertEquals(LocalDate.of(2023, 12, 31), result.getEndDate());
    assertEquals((byte) 0, result.getPaymentMethod());
  }

  @Test
  void testMapRowWithBothDatesNull() throws SQLException {
    when(mockResultSet.getLong("member_id")).thenReturn(4L);
    when(mockResultSet.getString("mail")).thenReturn("null@example.com");
    when(mockResultSet.getString("name")).thenReturn("Null User");
    when(mockResultSet.getString("address")).thenReturn("Null Address");
    when(mockResultSet.getDate("start_date")).thenReturn(null);
    when(mockResultSet.getDate("end_date")).thenReturn(null);
    when(mockResultSet.getByte("payment_method")).thenReturn((byte) 3);

    Member result = memberRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(4L, result.getMemberId());
    assertEquals("null@example.com", result.getMail());
    assertEquals("Null User", result.getName());
    assertEquals("Null Address", result.getAddress());
    assertNull(result.getStartDate());
    assertNull(result.getEndDate());
    assertEquals((byte) 3, result.getPaymentMethod());
  }

  @Test
  void testMapRowWithDifferentRowNumbers() throws SQLException {
    when(mockResultSet.getLong("member_id")).thenReturn(5L);
    when(mockResultSet.getString("mail")).thenReturn("row@example.com");
    when(mockResultSet.getString("name")).thenReturn("Row User");
    when(mockResultSet.getString("address")).thenReturn("Row Address");
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2023-03-15"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2023-09-15"));
    when(mockResultSet.getByte("payment_method")).thenReturn((byte) 1);

    Member result1 = memberRowMapper.mapRow(mockResultSet, 0);
    Member result2 = memberRowMapper.mapRow(mockResultSet, 100);

    assertNotNull(result1);
    assertNotNull(result2);
    assertEquals(result1.getMemberId(), result2.getMemberId());
    assertEquals(result1.getMail(), result2.getMail());
  }

  @Test
  void testMapRowWithEmptyStrings() throws SQLException {
    when(mockResultSet.getLong("member_id")).thenReturn(6L);
    when(mockResultSet.getString("mail")).thenReturn("");
    when(mockResultSet.getString("name")).thenReturn("");
    when(mockResultSet.getString("address")).thenReturn("");
    when(mockResultSet.getDate("start_date")).thenReturn(Date.valueOf("2023-01-01"));
    when(mockResultSet.getDate("end_date")).thenReturn(Date.valueOf("2023-12-31"));
    when(mockResultSet.getByte("payment_method")).thenReturn((byte) 255);

    Member result = memberRowMapper.mapRow(mockResultSet, 1);

    assertNotNull(result);
    assertEquals(6L, result.getMemberId());
    assertEquals("", result.getMail());
    assertEquals("", result.getName());
    assertEquals("", result.getAddress());
    assertEquals(LocalDate.of(2023, 1, 1), result.getStartDate());
    assertEquals(LocalDate.of(2023, 12, 31), result.getEndDate());
    assertEquals((byte) 255, result.getPaymentMethod());
  }
}