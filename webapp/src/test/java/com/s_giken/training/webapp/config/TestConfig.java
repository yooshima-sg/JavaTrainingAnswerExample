package com.s_giken.training.webapp.config;

import java.time.Clock;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@EnableJpaAuditing
public class TestConfig {

  @Bean
  @Primary
  public Clock testClock() {
    return Clock.systemDefaultZone();
  }
}
