package com.s_giken.training.webapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.s_giken.training.webapp.model.ChargeSearchCondition;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.service.ChargeService;

@ExtendWith(MockitoExtension.class)
class ChargeControllerTests {

  private MockMvc mockMvc;

  @Mock
  private ChargeService chargeService;

  @InjectMocks
  private ChargeController chargeController;

  @BeforeEach
  void setUp() {
    mockMvc = standaloneSetup(chargeController).build();
  }

  @Test
  void showSearchCondition_正常系() throws Exception {
    mockMvc.perform(get("/charge/search"))
        .andExpect(status().isOk())
        .andExpect(view().name("charge_search_condition"))
        .andExpect(model().attributeExists("chargeSearchCondition"));
  }

  @Test
  void searchAndListing_正常系() throws Exception {
    Charge charge = new Charge();
    charge.setChargeId(1);
    charge.setName("テスト料金");
    charge.setAmount(1000);
    charge.setStartDate(new Date());

    when(chargeService.findByConditions(any(ChargeSearchCondition.class)))
        .thenReturn(Arrays.asList(charge));

    mockMvc.perform(post("/charge/search")
        .param("name", "テスト")
        .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("charge_search_result"))
        .andExpect(model().attributeExists("result"));
  }

  @Test
  void editCharge_正常系() throws Exception {
    Charge charge = new Charge();
    charge.setChargeId(1);
    charge.setName("テスト料金");
    charge.setAmount(1000);
    charge.setStartDate(new Date());

    when(chargeService.findById(1)).thenReturn(Optional.of(charge));

    mockMvc.perform(get("/charge/edit/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("charge_edit"))
        .andExpect(model().attribute("chargeId", 1))
        .andExpect(model().attributeExists("charge"));
  }

  @Test
  void editCharge_存在しないID() throws Exception {
    when(chargeService.findById(999)).thenReturn(Optional.empty());

    mockMvc.perform(get("/charge/edit/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void addCharge_正常系() throws Exception {
    mockMvc.perform(get("/charge/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("charge_edit"))
        .andExpect(model().attributeExists("charge"));
  }

  @Test
  void saveCharge_正常系() throws Exception {
    Charge charge = new Charge();
    charge.setChargeId(1);

    doNothing().when(chargeService).save(any(Charge.class));

    mockMvc.perform(post("/charge/save")
        .param("chargeId", "1")
        .param("name", "テスト料金")
        .param("amount", "1000")
        .param("startDate", "2024-01-01")
        .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/charge/edit/1"));
  }

  @Test
  void deleteCharge_正常系() throws Exception {
    doNothing().when(chargeService).deleteById(1);

    mockMvc.perform(get("/charge/delete/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/charge/search"));
  }
}