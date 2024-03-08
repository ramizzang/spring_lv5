package com.sparta.shopapi.domain.member.repository;

import com.sparta.shopapi.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
