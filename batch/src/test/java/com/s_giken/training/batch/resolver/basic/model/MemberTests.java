package com.s_giken.training.batch.resolver.basic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MemberTests {

  @Test
  void testDefaultConstructor() {
    Member member = new Member();

    assertNotNull(member);
    assertNull(member.getMemberId());
    assertNull(member.getMail());
    assertNull(member.getName());
    assertNull(member.getAddress());
    assertNull(member.getStartDate());
    assertNull(member.getEndDate());
    assertNull(member.getPaymentMethod());
  }

  @Test
  void testAllArgsConstructor() {
    Long memberId = 1L;
    String mail = "test@example.com";
    String name = "テスト 太郎";
    String address = "東京都千代田区1-1-1";
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 12, 31);
    Byte paymentMethod = (byte) 1;

    Member member = new Member(memberId, mail, name, address, startDate, endDate, paymentMethod);

    assertEquals(memberId, member.getMemberId());
    assertEquals(mail, member.getMail());
    assertEquals(name, member.getName());
    assertEquals(address, member.getAddress());
    assertEquals(startDate, member.getStartDate());
    assertEquals(endDate, member.getEndDate());
    assertEquals(paymentMethod, member.getPaymentMethod());
  }

  @Test
  void testGettersAndSetters() {
    Member member = new Member();

    member.setMemberId(2L);
    member.setMail("user@test.com");
    member.setName("鈴木 一郎");
    member.setAddress("長野県長野市2-3-45");
    member.setStartDate(LocalDate.of(2023, 6, 15));
    member.setEndDate(LocalDate.of(2024, 6, 14));
    member.setPaymentMethod((byte) 2);

    assertEquals(2L, member.getMemberId());
    assertEquals("user@test.com", member.getMail());
    assertEquals("鈴木 一郎", member.getName());
    assertEquals("長野県長野市2-3-45", member.getAddress());
    assertEquals(LocalDate.of(2023, 6, 15), member.getStartDate());
    assertEquals(LocalDate.of(2024, 6, 14), member.getEndDate());
    assertEquals((byte) 2, member.getPaymentMethod());
  }

  @Test
  void testWithNullEndDate() {
    Member member = new Member();
    member.setMemberId(3L);
    member.setMail("active@example.com");
    member.setName("田中 次郎");
    member.setAddress("北海道札幌市9-8-7");
    member.setStartDate(LocalDate.of(2023, 1, 1));
    member.setEndDate(null);
    member.setPaymentMethod((byte) 1);

    assertNull(member.getEndDate());
    assertEquals(3L, member.getMemberId());
  }

  @Test
  void testPaymentMethodValues() {
    Member member = new Member();

    member.setPaymentMethod((byte) 0);
    assertEquals((byte) 0, member.getPaymentMethod());

    member.setPaymentMethod((byte) 255);
    assertEquals((byte) 255, member.getPaymentMethod());
  }
}
