package com.s_giken.training.webapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.model.entity.MemberSearchCondition;
import com.s_giken.training.webapp.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

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
    void testFindAll() {
        when(memberRepository.findAll()).thenReturn(testMembers);

        List<Member> result = memberService.findAll();

        assertEquals(2, result.size());
        assertEquals("test@example.com", result.get(0).getMail());
        assertEquals("test2@example.com", result.get(1).getMail());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testFindById_存在する場合() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(testMember));

        Optional<Member> result = memberService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getMail());
        assertEquals("テストユーザー", result.get().getName());
        verify(memberRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_存在しない場合() {
        when(memberRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Member> result = memberService.findById(999);

        assertFalse(result.isPresent());
        verify(memberRepository, times(1)).findById(999);
    }

    @Test
    void testFindByConditions() {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setMail("test");

        when(memberRepository.findByMailLike("%test%")).thenReturn(testMembers);

        List<Member> result = memberService.findByConditions(condition);

        assertEquals(2, result.size());
        verify(memberRepository, times(1)).findByMailLike("%test%");
    }

    @Test
    void testFindByConditions_空の検索条件() {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setMail("");

        when(memberRepository.findByMailLike("%%")).thenReturn(testMembers);

        List<Member> result = memberService.findByConditions(condition);

        assertEquals(2, result.size());
        verify(memberRepository, times(1)).findByMailLike("%%");
    }

    @Test
    void testSave() {
        when(memberRepository.save(any(Member.class))).thenReturn(testMember);

        memberService.save(testMember);

        verify(memberRepository, times(1)).save(testMember);
    }

    @Test
    void testDeleteById() {
        memberService.deleteById(1);

        verify(memberRepository, times(1)).deleteById(1);
    }
}
