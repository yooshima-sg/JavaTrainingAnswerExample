package com.s_giken.training.batch.resolver.allsql;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class AllSQLResolverTests {

  @Mock
  private JdbcTemplate mockJdbcTemplate;

  private AllSQLResolver allSQLResolver;

  @BeforeEach
  void setUp() {
    allSQLResolver = new AllSQLResolver(mockJdbcTemplate);
  }

  @Test
  void testConstructor() {
    assertNotNull(allSQLResolver);
  }

  @Test
  void testResolveWithValidArgs() throws Exception {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(0);
    when(mockJdbcTemplate.update(anyString(), any(LocalDate.class))).thenReturn(1);
    when(mockJdbcTemplate.update(anyString())).thenReturn(1);

    allSQLResolver.resolve(args);

    verify(mockJdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
    verify(mockJdbcTemplate, times(4)).update(anyString(), any(LocalDate.class));
    verify(mockJdbcTemplate, times(2)).update(anyString());
  }

  @Test
  void testResolveWithInvalidArgsLength() throws Exception {
    String[] args = {"202501", "extra"};

    allSQLResolver.resolve(args);

    verify(mockJdbcTemplate, never()).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
  }

  @Test
  void testResolveWithEmptyArgs() throws Exception {
    String[] args = {};

    allSQLResolver.resolve(args);

    verify(mockJdbcTemplate, never()).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
  }

  @Test
  void testResolveWithInvalidDateFormat() throws Exception {
    String[] args = {"invalid"};

    allSQLResolver.resolve(args);

    verify(mockJdbcTemplate, never()).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
  }

  @Test
  void testResolveWithAlreadyCommittedData() throws Exception {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(1);

    allSQLResolver.resolve(args);

    verify(mockJdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
    verify(mockJdbcTemplate, never()).update(anyString(), any(LocalDate.class));
    verify(mockJdbcTemplate, never()).update(anyString());
  }

  @Test
  void testResolveWithNullCount() throws Exception {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(null);

    assertThrows(Exception.class, () -> {
      allSQLResolver.resolve(args);
    });
  }

  @Test
  void testResolveWithValidArgsAllOperations() throws Exception {
    String[] args = {"202501"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(0);
    when(mockJdbcTemplate.update(anyString(), any(LocalDate.class))).thenReturn(5);
    when(mockJdbcTemplate.update(anyString())).thenReturn(10);

    allSQLResolver.resolve(args);

    verify(mockJdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
    verify(mockJdbcTemplate, times(4)).update(anyString(), any(LocalDate.class));
    verify(mockJdbcTemplate, times(2)).update(anyString());
  }

  @Test
  void testResolveWithDifferentValidMonth() throws Exception {
    String[] args = {"202512"};
    when(mockJdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
        .thenReturn(0);
    when(mockJdbcTemplate.update(anyString(), any(LocalDate.class))).thenReturn(1);
    when(mockJdbcTemplate.update(anyString())).thenReturn(1);

    allSQLResolver.resolve(args);

    verify(mockJdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class),
        any(LocalDate.class));
    verify(mockJdbcTemplate, times(4)).update(anyString(), any(LocalDate.class));
    verify(mockJdbcTemplate, times(2)).update(anyString());
  }
}
