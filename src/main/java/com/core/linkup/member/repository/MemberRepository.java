package com.core.linkup.member.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.util.annotation.NonNullApi;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findFirstByEmail(String email);
    @NonNull
    Optional<Member> findById(Long id);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    default Member findByMemberId(Long id){
        return findById(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER));
    }

    // for login
    default Member findByUserEmail(String email){
        return findFirstByEmail(email).orElseThrow(
                () -> new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER)
        );
    }
}
