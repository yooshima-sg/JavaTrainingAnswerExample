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

import com.s_giken.training.webapp.model.MemberSearchCondition;
import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTests {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private MemberServiceImpl memberService;

  private Member testMember;

  @BeforeEach
  void setUp() {
    testMember = new Member();
    testMember.setMemberId(1L);
    testMember.setName("テスト太郎");
    testMember.setMail("test@example.com");
    testMember.setAddress("東京都");
    testMember.setStartDate(new Date());
    testMember.setPaymentMethod(1);
  }

  @Test
  void findAll_正常系() {
    List<Member> expectedMembers = Arrays.asList(testMember);
    when(memberRepository.findAll()).thenReturn(expectedMembers);

    List<Member> actualMembers = memberService.findAll();

    assertEquals(expectedMembers, actualMembers);
    verify(memberRepository, times(1)).findAll();
  }

  @Test
  void findById_正常系_存在するID() {
    when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));

    Optional<Member> result = memberService.findById(1L);

    assertTrue(result.isPresent());
    assertEquals(testMember, result.get());
    verify(memberRepository, times(1)).findById(1L);
  }

  @Test
  void findById_正常系_存在しないID() {
    when(memberRepository.findById(999L)).thenReturn(Optional.empty());

    Optional<Member> result = memberService.findById(999L);

    assertFalse(result.isPresent());
    verify(memberRepository, times(1)).findById(999L);
  }

  @Test
  void findByConditions_正常系_昇順() {
    MemberSearchCondition condition = new MemberSearchCondition();
    condition.setName("テスト");
    condition.setMail("test");
    condition.setSortColName("name");
    condition.setSortOrder("asc");

    List<Member> expectedMembers = Arrays.asList(testMember);
    when(memberRepository.findByNameLikeAndMailLike(anyString(), anyString(), any(Sort.class)))
        .thenReturn(expectedMembers);

    List<Member> actualMembers = memberService.findByConditions(condition);

    assertEquals(expectedMembers, actualMembers);
    verify(memberRepository, times(1))
        .findByNameLikeAndMailLike("%テスト%", "%test%", Sort.by(Sort.Direction.ASC, "name"));
  }

  @Test
  void findByConditions_正常系_降順() {
    MemberSearchCondition condition = new MemberSearchCondition();
    condition.setName("テスト");
    condition.setMail("test");
    condition.setSortColName("name");
    condition.setSortOrder("desc");

    List<Member> expectedMembers = Arrays.asList(testMember);
    when(memberRepository.findByNameLikeAndMailLike(anyString(), anyString(), any(Sort.class)))
        .thenReturn(expectedMembers);

    List<Member> actualMembers = memberService.findByConditions(condition);

    assertEquals(expectedMembers, actualMembers);
    verify(memberRepository, times(1))
        .findByNameLikeAndMailLike("%テスト%", "%test%", Sort.by(Sort.Direction.DESC, "name"));
  }

  @Test
  void save_正常系() {
    when(memberRepository.save(testMember)).thenReturn(testMember);

    memberService.save(testMember);

    verify(memberRepository, times(1)).save(testMember);
  }

  @Test
  void deleteById_正常系() {
    doNothing().when(memberRepository).deleteById(1L);

    memberService.deleteById(1L);

    verify(memberRepository, times(1)).deleteById(1L);
  }
}
