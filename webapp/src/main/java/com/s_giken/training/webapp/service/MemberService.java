package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;
import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.model.MemberSearchCondition;

/*
 * 【ヒント】
 * MemberService をインターフェースとして定義することで、
 * インターフェースを実体化するクラスができることを明確化する。
 */

/**
 * 加入者情報ビジネスロジック(サービス)インターフェース
 */
public interface MemberService {
    public List<Member> findAll();

    public Optional<Member> findById(Long memberId);

    public List<Member> findByConditions(MemberSearchCondition memberSearchCondition);

    public void add(Member member);

    public void update(Member member);

    public void deleteById(Long memberId);
}
