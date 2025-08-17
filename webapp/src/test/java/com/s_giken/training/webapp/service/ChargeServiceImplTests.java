package com.s_giken.training.webapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.s_giken.training.webapp.model.ChargeSearchCondition;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.repository.ChargeRepository;

@ExtendWith(MockitoExtension.class)
class ChargeServiceImplTests {

  @Mock
  private ChargeRepository chargeRepository;

  @InjectMocks
  private ChargeServiceImpl chargeService;

  private Charge testCharge;

  @BeforeEach
  void setUp() {
    testCharge = new Charge();
    testCharge.setChargeId(1L);
    testCharge.setName("テスト料金");
    testCharge.setAmount(new BigDecimal("1000"));
    testCharge.setStartDate(new Date());
  }

  @Test
  void findAll_正常系() {
    List<Charge> expectedCharges = Arrays.asList(testCharge);
    when(chargeRepository.findAll()).thenReturn(expectedCharges);

    List<Charge> actualCharges = chargeService.findAll();

    assertEquals(expectedCharges, actualCharges);
    verify(chargeRepository, times(1)).findAll();
  }

  @Test
  void findById_正常系_存在するID() {
    when(chargeRepository.findById(1L)).thenReturn(Optional.of(testCharge));

    Optional<Charge> result = chargeService.findById(1L);

    assertTrue(result.isPresent());
    assertEquals(testCharge, result.get());
    verify(chargeRepository, times(1)).findById(1L);
  }

  @Test
  void findById_正常系_存在しないID() {
    when(chargeRepository.findById(999L)).thenReturn(Optional.empty());

    Optional<Charge> result = chargeService.findById(999L);

    assertFalse(result.isPresent());
    verify(chargeRepository, times(1)).findById(999L);
  }

  @Test
  void findByConditions_正常系_昇順() {
    ChargeSearchCondition condition = new ChargeSearchCondition();
    condition.setName("テスト");
    condition.setSortColName("name");
    condition.setSortOrder("asc");

    List<Charge> expectedCharges = Arrays.asList(testCharge);
    when(chargeRepository.findByNameLike(anyString(), any(Sort.class)))
        .thenReturn(expectedCharges);

    List<Charge> actualCharges = chargeService.findByConditions(condition);

    assertEquals(expectedCharges, actualCharges);
    verify(chargeRepository, times(1))
        .findByNameLike("%テスト%", Sort.by(Sort.Direction.ASC, "name"));
  }

  @Test
  void findByConditions_正常系_降順() {
    ChargeSearchCondition condition = new ChargeSearchCondition();
    condition.setName("テスト");
    condition.setSortColName("name");
    condition.setSortOrder("desc");

    List<Charge> expectedCharges = Arrays.asList(testCharge);
    when(chargeRepository.findByNameLike(anyString(), any(Sort.class)))
        .thenReturn(expectedCharges);

    List<Charge> actualCharges = chargeService.findByConditions(condition);

    assertEquals(expectedCharges, actualCharges);
    verify(chargeRepository, times(1))
        .findByNameLike("%テスト%", Sort.by(Sort.Direction.DESC, "name"));
  }

  @Test
  void save_正常系() {
    when(chargeRepository.save(testCharge)).thenReturn(testCharge);

    chargeService.save(testCharge);

    verify(chargeRepository, times(1)).save(testCharge);
  }

  @Test
  void deleteById_正常系() {
    doNothing().when(chargeRepository).deleteById(1L);

    chargeService.deleteById(1L);

    verify(chargeRepository, times(1)).deleteById(1L);
  }
}
