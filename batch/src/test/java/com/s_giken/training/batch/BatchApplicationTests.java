package com.s_giken.training.batch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.s_giken.training.batch.resolver.IResolver;

@ExtendWith(MockitoExtension.class)
class BatchApplicationTests {

  @Mock
  private IResolver mockResolver;

  private BatchApplication batchApplication;

  @BeforeEach
  void setUp() {
    batchApplication = new BatchApplication(mockResolver);
  }

  @Test
  void testConstructor() {
    assertNotNull(batchApplication);
  }

  @Test
  void testRunCallsResolverWithArgs() throws Exception {
    String[] args = {"202301"};

    batchApplication.run(args);

    verify(mockResolver).resolve(args);
  }

  @Test
  void testRunWithEmptyArgs() throws Exception {
    String[] args = {};

    batchApplication.run(args);

    verify(mockResolver).resolve(args);
  }

  @Test
  void testRunWithMultipleArgs() throws Exception {
    String[] args = {"202301", "extra"};

    batchApplication.run(args);

    verify(mockResolver).resolve(args);
  }

  @Test
  void testRunPropagatesExceptionFromResolver() throws Exception {
    String[] args = {"202301"};
    doThrow(new RuntimeException("Test exception")).when(mockResolver).resolve(any(String[].class));

    try {
      batchApplication.run(args);
    } catch (RuntimeException e) {
      // Expected
    }

    verify(mockResolver).resolve(args);
  }
}
