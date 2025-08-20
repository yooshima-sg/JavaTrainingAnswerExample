package com.s_giken.training.batch.resolver.basic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class BatchArgsTests {

  @Test
  void testDefaultConstructor() {
    BatchArgs batchArgs = new BatchArgs();
    assertNotNull(batchArgs);
    assertEquals(LocalDate.of(2000, 1, 1), batchArgs.getTargetDate());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDate targetDate = LocalDate.of(2025, 8, 15);
    BatchArgs batchArgs = new BatchArgs(targetDate);

    assertNotNull(batchArgs);
    assertEquals(targetDate, batchArgs.getTargetDate());
  }

  @Test
  void testGetterAndSetter() {
    BatchArgs batchArgs = new BatchArgs();
    LocalDate newDate = LocalDate.of(2025, 12, 25);

    batchArgs.setTargetDate(newDate);

    assertEquals(newDate, batchArgs.getTargetDate());
  }

  @Test
  void testGetTargetDateForLog() {
    BatchArgs batchArgs = new BatchArgs();
    LocalDate testDate = LocalDate.of(2025, 3, 1);
    batchArgs.setTargetDate(testDate);

    String result = batchArgs.getTargetDateForLog();

    assertEquals("2025年03月", result);
  }

  @Test
  void testGetTargetDateForLogWithDefaultDate() {
    BatchArgs batchArgs = new BatchArgs();

    String result = batchArgs.getTargetDateForLog();

    assertEquals("2000年01月", result);
  }

  @Test
  void testGetTargetDateForLogWithDifferentMonths() {
    BatchArgs batchArgs = new BatchArgs();

    batchArgs.setTargetDate(LocalDate.of(2025, 1, 1));
    assertEquals("2025年01月", batchArgs.getTargetDateForLog());

    batchArgs.setTargetDate(LocalDate.of(2025, 12, 1));
    assertEquals("2025年12月", batchArgs.getTargetDateForLog());
  }

  @Test
  void testToString() {
    LocalDate date = LocalDate.of(2025, 6, 1);
    BatchArgs batchArgs = new BatchArgs(date);

    String result = batchArgs.toString();

    assertNotNull(result);
    assertEquals(true, result.contains("targetDate"));
  }
}
