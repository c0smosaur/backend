package com.core.linkup.member.repository;

import com.core.linkup.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findFirstByEmail(String email);
    Optional<Member> findFirstByUuid(UUID uuid);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    // for jwt authentication
    default Member findByUuid(UUID uuid){
        return findFirstByUuid(uuid).orElseThrow(
                () -> new RuntimeException("User not found"));
    };

    default Member findByUuid(UUID uuid, String message){
        return findFirstByUuid(uuid).orElseThrow(
                () -> new RuntimeException(message)
        );
    }

    // for login
    default Member findByUserEmail(String email){
        return findFirstByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found"));
    };

    default Member findByUserEmail(String email, String message){
        return findFirstByEmail(email).orElseThrow(
                () -> new RuntimeException(message)
        );
    }
}
