package com.core.linkup.security;

import com.core.linkup.member.entity.Member;

import com.core.linkup.member.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class MemberDetails implements UserDetails {
    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        assert member != null;
        // single authority, (enum) role type
        RoleType role = member.getRole();
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

//    multiple authority, (db)role entity
//    private Member member;
//
//    public CustomerMember(Member member){
//        super(member.getUsername(), member.getPassword(), getAuthorities(member.getRoles()));
//        this.member = member;
//    }
//
//    private static Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles){
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    public Long getId() {
        return member.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
