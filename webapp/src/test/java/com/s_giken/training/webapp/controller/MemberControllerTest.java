package com.s_giken.training.webapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.model.entity.MemberSearchCondition;
import com.s_giken.training.webapp.service.MemberService;

@WebMvcTest(MemberController.class)
@WithMockUser
@ActiveProfiles("test")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;
    
    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    private Member testMember;
    private List<Member> testMembers;

    @BeforeEach
    void setUp() {
        testMember = new Member();
        testMember.setMemberId(1);
        testMember.setMail("test@example.com");
        testMember.setName("テストユーザー");
        testMember.setAddress("東京都");
        testMember.setStartDate(new Date());
        testMember.setPaymentMethod(1);

        Member testMember2 = new Member();
        testMember2.setMemberId(2);
        testMember2.setMail("test2@example.com");
        testMember2.setName("テストユーザー2");
        testMember2.setAddress("大阪府");
        testMember2.setStartDate(new Date());
        testMember2.setPaymentMethod(2);

        testMembers = Arrays.asList(testMember, testMember2);
    }

    @Test
    void testShowSearchCondition() throws Exception {
        mockMvc.perform(get("/member/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("member_search_condition"))
                .andExpect(model().attributeExists("memberSearchCondition"));
    }

    @Test
    void testSearchAndListing() throws Exception {
        when(memberService.findByConditions(any(MemberSearchCondition.class)))
                .thenReturn(testMembers);

        mockMvc.perform(post("/member/search")
                .param("mail", "test")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("member_search_result"))
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attribute("result", testMembers));
    }

    @Test
    void testEditMember_存在する場合() throws Exception {
        when(memberService.findById(1)).thenReturn(Optional.of(testMember));

        mockMvc.perform(get("/member/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("member_edit"))
                .andExpect(model().attributeExists("member"));
    }

    @Test
    void testEditMember_存在しない場合() throws Exception {
        when(memberService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/member/edit/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddMember() throws Exception {
        mockMvc.perform(get("/member/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("member_edit"))
                .andExpect(model().attributeExists("member"));
    }

    @Test
    void testSaveMember_正常な場合() throws Exception {
        doNothing().when(memberService).save(any(Member.class));

        mockMvc.perform(post("/member/save")
                .param("memberId", "1")
                .param("mail", "test@example.com")
                .param("name", "テストユーザー")
                .param("address", "東京都")
                .param("startDate", "2024-01-01")
                .param("paymentMethod", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/edit/1"));
    }

    @Test
    void testSaveMember_バリデーションエラー() throws Exception {
        mockMvc.perform(post("/member/save")
                .param("memberId", "1")
                .param("mail", "")
                .param("name", "")
                .param("address", "")
                .param("startDate", "")
                .param("paymentMethod", "")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("member_edit"));
    }

    @Test
    void testDeleteMember_存在する場合() throws Exception {
        when(memberService.findById(1)).thenReturn(Optional.of(testMember));
        doNothing().when(memberService).deleteById(1);

        mockMvc.perform(get("/member/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/search"));
    }

    @Test
    void testDeleteMember_存在しない場合() throws Exception {
        when(memberService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/member/delete/999"))
                .andExpect(status().isNotFound());
    }
}
