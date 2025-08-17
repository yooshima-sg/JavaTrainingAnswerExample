package com.s_giken.training.webapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;

import com.s_giken.training.webapp.model.entity.Member;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private MemberRepository memberRepository;

  private Member testMember;

  @BeforeEach
  void setUp() {
    testMember = new Member();
    testMember.setName("テスト太郎");
    testMember.setMail("test@example.com");
    testMember.setAddress("東京都");
    testMember.setStartDate(new Date());
    testMember.setPaymentMethod(1);
  }

  @Test
  void save_正常系() {
    Member savedMember = memberRepository.save(testMember);

    assertTrue(savedMember.getMemberId() != null);
    assertEquals("テスト太郎", savedMember.getName());
    assertEquals("test@example.com", savedMember.getMail());
  }

  @Test
  void findById_正常系() {
    testMember = entityManager.persistAndFlush(testMember);

    Optional<Member> result = memberRepository.findById(testMember.getMemberId());

    assertTrue(result.isPresent());
    assertEquals("テスト太郎", result.get().getName());
  }

  @Test
  void findAll_正常系() {
    entityManager.persistAndFlush(testMember);

    List<Member> members = memberRepository.findAll();

    assertFalse(members.isEmpty());
    assertTrue(members.stream().anyMatch(m -> "テスト太郎".equals(m.getName())));
  }

  @Test
  void findByNameLikeAndMailLike_正常系() {
    entityManager.persistAndFlush(testMember);

    List<Member> result = memberRepository.findByNameLikeAndMailLike("%テスト%", "%test%");

    assertFalse(result.isEmpty());
    assertEquals("テスト太郎", result.get(0).getName());
  }

  @Test
  void findByNameLikeAndMailLike_正常系_ソート指定() {
    Member member2 = new Member();
    member2.setName("あいうえお");
    member2.setMail("test2@example.com");
    member2.setAddress("大阪府");
    member2.setStartDate(new Date());
    member2.setPaymentMethod(2);

    entityManager.persistAndFlush(testMember);
    entityManager.persistAndFlush(member2);

    List<Member> result = memberRepository.findByNameLikeAndMailLike(
        "%", "%test%", Sort.by(Sort.Direction.ASC, "name"));

    assertEquals(2, result.size());
    assertEquals("あいうえお", result.get(0).getName());
    assertEquals("テスト太郎", result.get(1).getName());
  }

  @Test
  void deleteById_正常系() {
    testMember = entityManager.persistAndFlush(testMember);
    Long memberId = testMember.getMemberId();

    memberRepository.deleteById(memberId);

    Optional<Member> result = memberRepository.findById(memberId);
    assertFalse(result.isPresent());
  }
}
