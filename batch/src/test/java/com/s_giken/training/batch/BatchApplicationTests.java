package com.s_giken.training.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class BatchApplicationTests {

  @Mock
  private JdbcTemplate jdbcTemplate;

  private BatchApplication batchApplication;

  // @BeforeEach
  // void setUp() {
  //   batchApplication = new BatchApplication(jdbcTemplate);
  // }

  // @Test
  // void testConstructor() {
  //   assertNotNull(batchApplication);
  // }

  // @Test
  // void testParseArgsValid() throws Exception {
  //   Method parseArgsMethod = BatchApplication.class.getDeclaredMethod("parseArgs", String[].class);
  //   parseArgsMethod.setAccessible(true);

  //   String[] args = {"202301"};
  //   LocalDate result = (LocalDate) parseArgsMethod.invoke(batchApplication, (Object) args);

  //   assertEquals(LocalDate.of(2023, 1, 1), result);
  // }

  // @Test
  // void testParseArgsInvalidLength() throws Exception {
  //   Method parseArgsMethod = BatchApplication.class.getDeclaredMethod("parseArgs", String[].class);
  //   parseArgsMethod.setAccessible(true);

  //   String[] args = {"202301", "extra"};
  //   Exception exception = assertThrows(Exception.class, () -> {
  //     parseArgsMethod.invoke(batchApplication, (Object) args);
  //   });

  //   assertTrue(exception.getCause().getMessage().contains("コマンドライン引数が正しくありません。"));
  // }

  // @Test
  // void testParseArgsInvalidFormat() throws Exception {
  //   Method parseArgsMethod = BatchApplication.class.getDeclaredMethod("parseArgs", String[].class);
  //   parseArgsMethod.setAccessible(true);

  //   String[] args = {"invalid"};
  //   assertThrows(Exception.class, () -> {
  //     parseArgsMethod.invoke(batchApplication, (Object) args);
  //   });
  // }

  // @Test
  // void testRunWithValidArgs() throws Exception {
  //   when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
  //       .thenReturn(0);
  //   when(jdbcTemplate.update(anyString(), any(LocalDate.class))).thenReturn(1);
  //   when(jdbcTemplate.update(anyString())).thenReturn(1);

  //   String[] args = {"202301"};
  //   batchApplication.run(args);

  //   verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class), any(LocalDate.class));
  //   verify(jdbcTemplate, times(4)).update(anyString(), any(LocalDate.class));
  //   verify(jdbcTemplate, times(2)).update(anyString());
  // }

  // @Test
  // void testRunWithInvalidArgs() throws Exception {
  //   ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  //   PrintStream originalErr = System.err;
  //   System.setErr(new PrintStream(outputStream));

  //   try {
  //     String[] args = {"invalid"};
  //     batchApplication.run(args);

  //     verify(jdbcTemplate, never()).queryForObject(anyString(), eq(Integer.class), any(LocalDate.class));
  //   } finally {
  //     System.setErr(originalErr);
  //   }
  // }

  // @Test
  // void testRunWithAlreadyCommittedData() throws Exception {
  //   when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
  //       .thenReturn(1);

  //   String[] args = {"202301"};
  //   batchApplication.run(args);

  //   verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class), any(LocalDate.class));
  //   verify(jdbcTemplate, never()).update(anyString(), any(LocalDate.class));
  //   verify(jdbcTemplate, never()).update(anyString());
  // }

  // @Test
  // void testAppendBillingDataWithNullCount() throws Exception {
  //   when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
  //       .thenReturn(null);

  //   Method appendBillingDataMethod = BatchApplication.class.getDeclaredMethod("appendBillingData", LocalDate.class);
  //   appendBillingDataMethod.setAccessible(true);

  //   LocalDate targetYm = LocalDate.of(2023, 1, 1);
  //   Exception exception = assertThrows(Exception.class, () -> {
  //     appendBillingDataMethod.invoke(batchApplication, targetYm);
  //   });

  //   assertTrue(exception.getCause().getMessage().contains("count変数がnullになっています。"));
  // }

  // @Test
  // void testAppendBillingDataSuccess() throws Exception {
  //   when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(LocalDate.class)))
  //       .thenReturn(0);
  //   when(jdbcTemplate.update(anyString(), any(LocalDate.class))).thenReturn(1);
  //   when(jdbcTemplate.update(anyString())).thenReturn(1);

  //   Method appendBillingDataMethod = BatchApplication.class.getDeclaredMethod("appendBillingData", LocalDate.class);
  //   appendBillingDataMethod.setAccessible(true);

  //   LocalDate targetYm = LocalDate.of(2023, 1, 1);
  //   appendBillingDataMethod.invoke(batchApplication, targetYm);

  //   verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class), any(LocalDate.class));
  //   verify(jdbcTemplate, times(4)).update(anyString(), any(LocalDate.class));
  //   verify(jdbcTemplate, times(2)).update(anyString());
  // }
}
