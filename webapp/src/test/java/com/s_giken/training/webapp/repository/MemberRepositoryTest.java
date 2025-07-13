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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.s_giken.training.webapp.model.entity.Member;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember1;
    private Member testMember2;

    @BeforeEach
    void setUp() {
        testMember1 = new Member();
        testMember1.setMail("tanaka@example.com");
        testMember1.setName("田中太郎");
        testMember1.setAddress("東京都渋谷区");
        testMember1.setStartDate(new Date());
        testMember1.setPaymentMethod(1);

        testMember2 = new Member();
        testMember2.setMail("sato@example.com");
        testMember2.setName("佐藤花子");
        testMember2.setAddress("大阪府大阪市");
        testMember2.setStartDate(new Date());
        testMember2.setPaymentMethod(2);

        entityManager.persistAndFlush(testMember1);
        entityManager.persistAndFlush(testMember2);
    }

    @Test
    void testFindByMailLike_部分一致() {
        List<Member> result = memberRepository.findByMailLike("%tanaka%");

        assertEquals(1, result.size());
        assertEquals("tanaka@example.com", result.get(0).getMail());
        assertEquals("田中太郎", result.get(0).getName());
    }

    @Test
    void testFindByMailLike_複数一致() {
        List<Member> result = memberRepository.findByMailLike("%@example.com%");

        assertEquals(2, result.size());
    }

    @Test
    void testFindByMailLike_一致なし() {
        List<Member> result = memberRepository.findByMailLike("%notfound%");

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMailLike_空文字() {
        List<Member> result = memberRepository.findByMailLike("%%");

        assertEquals(2, result.size());
    }

    @Test
    void testSave() {
        Member newMember = new Member();
        newMember.setMail("yamada@example.com");
        newMember.setName("山田次郎");
        newMember.setAddress("福岡県福岡市");
        newMember.setStartDate(new Date());
        newMember.setPaymentMethod(1);

        Member savedMember = memberRepository.save(newMember);

        assertTrue(savedMember.getMemberId() > 0);
        assertEquals("yamada@example.com", savedMember.getMail());
        assertEquals("山田次郎", savedMember.getName());
    }

    @Test
    void testFindById() {
        Optional<Member> result = memberRepository.findById(testMember1.getMemberId());

        assertTrue(result.isPresent());
        assertEquals("tanaka@example.com", result.get().getMail());
    }

    @Test
    void testDeleteById() {
        int memberId = testMember1.getMemberId();
        memberRepository.deleteById(memberId);

        Optional<Member> result = memberRepository.findById(memberId);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<Member> result = memberRepository.findAll();

        assertEquals(2, result.size());
    }
}