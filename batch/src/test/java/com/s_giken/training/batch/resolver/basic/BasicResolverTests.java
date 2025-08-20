package com.s_giken.training.batch.resolver.basic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.s_giken.training.batch.resolver.basic.mapper.ChargeRowMapper;
import com.s_giken.training.batch.resolver.basic.mapper.MemberRowMapper;
import com.s_giken.training.batch.resolver.basic.model.Charge;
import com.s_giken.training.batch.resolver.basic.model.Member;

@ExtendWith(MockitoExtension.class)
class BasicResolverTests {

  @Mock
  private JdbcTemplate mockJdbcTemplate;

  @Mock
  private MemberRowMapper mockMemberRowMapper;

  @Mock
  private ChargeRowMapper mockChargeRowMapper;

  private BasicResolver basicResolver;

  @BeforeEach
  void setUp() {
    basicResolver = new BasicResolver(mockJdbcTemplate, mockMemberRowMapper, mockChargeRowMapper);
  }

  @Test
  void testConstructor() {
    assertNotNull(basicResolver);
  }

  @Test
  void testResolveWithValidArgs() {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(0);
    when(mockJdbcTemplate.update(anyString(), any(LocalDate.class))).thenReturn(1);
    when(mockJdbcTemplate.query(anyString(), eq(mockMemberRowMapper), any(LocalDate.class),
        any(LocalDate.class)))
            .thenReturn(createMockMemberList());
    when(mockJdbcTemplate.query(anyString(), eq(mockChargeRowMapper), any(LocalDate.class),
        any(LocalDate.class)))
            .thenReturn(createMockChargeList());

    basicResolver.resolve(args);

    verify(mockJdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
    verify(mockJdbcTemplate, times(4)).update(anyString(), any(LocalDate.class));
  }

  @Test
  void testResolveWithInvalidDateFormat() {
    String[] args = {"invalid"};

    basicResolver.resolve(args);

    verify(mockJdbcTemplate, never()).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
  }

  @Test
  void testResolveWithEmptyArgs() {
    String[] args = {};

    basicResolver.resolve(args);

    verify(mockJdbcTemplate, never()).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
  }

  @Test
  void testResolveWithAlreadyCommittedData() {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(1);

    basicResolver.resolve(args);

    verify(mockJdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
    verify(mockJdbcTemplate, never()).update(anyString(), any(LocalDate.class));
  }

  @Test
  void testResolveWithNullBillingStatusCount() {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(null);

    assertThrows(RuntimeException.class, () -> {
      basicResolver.resolve(args);
    });
  }

  @Test
  void testResolveWithEmptyMemberList() {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(0);
    when(mockJdbcTemplate.update(anyString(), any(LocalDate.class))).thenReturn(1);
    when(mockJdbcTemplate.query(anyString(), eq(mockMemberRowMapper), any(LocalDate.class),
        any(LocalDate.class)))
            .thenReturn(Collections.emptyList());
    when(mockJdbcTemplate.query(anyString(), eq(mockChargeRowMapper), any(LocalDate.class),
        any(LocalDate.class)))
            .thenReturn(createMockChargeList());

    basicResolver.resolve(args);

    verify(mockJdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
    verify(mockJdbcTemplate, times(4)).update(anyString(), any(LocalDate.class));
  }

  @Test
  void testResolveWithEmptyChargeList() {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(0);
    when(mockJdbcTemplate.update(anyString(), any(LocalDate.class))).thenReturn(1);
    when(mockJdbcTemplate.query(anyString(), eq(mockMemberRowMapper), any(LocalDate.class),
        any(LocalDate.class)))
            .thenReturn(createMockMemberList());
    when(mockJdbcTemplate.query(anyString(), eq(mockChargeRowMapper), any(LocalDate.class),
        any(LocalDate.class)))
            .thenReturn(Collections.emptyList());

    basicResolver.resolve(args);

    verify(mockJdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
    verify(mockJdbcTemplate, times(4)).update(anyString(), any(LocalDate.class));
  }

  private List<Member> createMockMemberList() {
    Member member1 = new Member();
    member1.setMemberId(1L);
    member1.setMail("test1@example.com");
    member1.setName("Test User 1");
    member1.setAddress("Test Address 1");
    member1.setStartDate(LocalDate.of(2025, 1, 1));
    member1.setEndDate(null);
    member1.setPaymentMethod((byte) 1);

    Member member2 = new Member();
    member2.setMemberId(2L);
    member2.setMail("test2@example.com");
    member2.setName("Test User 2");
    member2.setAddress("Test Address 2");
    member2.setStartDate(LocalDate.of(2025, 1, 15));
    member2.setEndDate(LocalDate.of(2025, 12, 31));
    member2.setPaymentMethod((byte) 2);

    return Arrays.asList(member1, member2);
  }

  private List<Charge> createMockChargeList() {
    Charge charge1 = new Charge();
    charge1.setChargeId(1L);
    charge1.setChargeName("Basic Plan");
    charge1.setAmount(new BigDecimal("1000"));
    charge1.setStartDate(LocalDate.of(2025, 1, 1));
    charge1.setEndDate(null);

    Charge charge2 = new Charge();
    charge2.setChargeId(2L);
    charge2.setChargeName("Premium Plan");
    charge2.setAmount(new BigDecimal("2000"));
    charge2.setStartDate(LocalDate.of(2025, 1, 1));
    charge2.setEndDate(LocalDate.of(2025, 12, 31));

    return Arrays.asList(charge1, charge2);
  }
}
