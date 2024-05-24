package com.core.linkup.member.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.common.entity.enums.GenderType;
import com.core.linkup.common.entity.enums.IndustryType;
import com.core.linkup.common.entity.enums.OccupationType;
import com.core.linkup.common.entity.enums.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    private IndustryType industry;

    @Enumerated(EnumType.STRING)
    private OccupationType occupation;

    private LocalDate birthday;

    private String profileImage;

    private String introduction;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String currentLocation;

    // TODO: 좋아요(북마크) 추가

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name="role",
//            joinColumns = @JoinColumn(name="member_id"), // 왼쪽 참조할 땐 joinColumns. FOREIGN KEY, PK 참조
//            inverseJoinColumns = @JoinColumn(name = "role_id") // 오른쪽 참조할 땐 inverseJoinColumns. FOREIGN KEY, PK 참조
//    )
//    private Set<Role> roles;

//    @ElementCollection (multiple enum)
//    private Set<RoleType> roles;

}
