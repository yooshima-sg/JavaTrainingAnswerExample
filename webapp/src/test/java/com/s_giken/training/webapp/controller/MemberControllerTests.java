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

import com.s_giken.training.webapp.model.MemberSearchCondition;
import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.service.MemberService;

@ExtendWith(MockitoExtension.class)
class MemberControllerTests {

  private MockMvc mockMvc;

  @Mock
  private MemberService memberService;

  @InjectMocks
  private MemberController memberController;

  @BeforeEach
  void setUp() {
    mockMvc = standaloneSetup(memberController).build();
  }

  @Test
  void showSearchCondition_正常系() throws Exception {
    mockMvc.perform(get("/member/search"))
        .andExpect(status().isOk())
        .andExpect(view().name("member_search_condition"))
        .andExpect(model().attributeExists("memberSearchCondition"));
  }

  @Test
  void searchAndListing_正常系() throws Exception {
    Member member = new Member();
    member.setMemberId(1L);
    member.setName("テスト太郎");
    member.setMail("test@example.com");
    member.setAddress("東京都");
    member.setStartDate(new Date());
    member.setPaymentMethod(1);

    when(memberService.findByConditions(any(MemberSearchCondition.class)))
        .thenReturn(Arrays.asList(member));

    mockMvc.perform(post("/member/search")
        .param("name", "テスト")
        .param("mail", "test")
        .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("member_search_result"))
        .andExpect(model().attributeExists("result"));
  }

  @Test
  void editMember_正常系() throws Exception {
    Member member = new Member();
    member.setMemberId(1L);
    member.setName("テスト太郎");
    member.setMail("test@example.com");
    member.setAddress("東京都");
    member.setStartDate(new Date());
    member.setPaymentMethod(1);

    when(memberService.findById(1L)).thenReturn(Optional.of(member));

    mockMvc.perform(get("/member/edit/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("member_edit"))
        .andExpect(model().attribute("memberId", 1L))
        .andExpect(model().attributeExists("member"));
  }

  @Test
  void editMember_存在しないID() throws Exception {
    when(memberService.findById(999L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/member/edit/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void addMember_正常系() throws Exception {
    mockMvc.perform(get("/member/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("member_edit"))
        .andExpect(model().attributeExists("member"));
  }

  @Test
  void saveMember_正常系() throws Exception {
    Member member = new Member();
    member.setMemberId(1L);

    doNothing().when(memberService).save(any(Member.class));

    mockMvc.perform(post("/member/save")
        .param("memberId", "1")
        .param("name", "テスト太郎")
        .param("mail", "test@example.com")
        .param("address", "東京都")
        .param("startDate", "2024-01-01")
        .param("paymentMethod", "1")
        .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/member/edit/1"));
  }

  @Test
  void deleteMember_正常系() throws Exception {
    Member member = new Member();
    member.setMemberId(1L);

    when(memberService.findById(1L)).thenReturn(Optional.of(member));
    doNothing().when(memberService).deleteById(1L);

    mockMvc.perform(get("/member/delete/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/member/search"));
  }

  @Test
  void deleteMember_存在しないID() throws Exception {
    when(memberService.findById(999L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/member/delete/999"))
        .andExpect(status().isNotFound());
  }
}
