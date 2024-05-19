package com.core.linkup.member.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findFirstByEmail(String email);
    Optional<Member> findFirstByUuid(UUID uuid);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    // null 예외는 repository에서 잡고 감
    // for jwt authentication
    default Member findByUuid(UUID uuid){
        return findFirstByUuid(uuid).orElseThrow(
                () -> new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER));
    }

    // for login
    default Member findByUserEmail(String email){
        return findFirstByEmail(email).orElseThrow(
                () -> new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER)
        );
    }
}
