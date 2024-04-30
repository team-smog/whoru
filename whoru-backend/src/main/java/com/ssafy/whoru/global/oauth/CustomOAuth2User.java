package com.ssafy.whoru.global.oauth;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final MemberDTO memberDTO;

    public CustomOAuth2User(MemberDTO memberDTO) {

        this.memberDTO = memberDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getName() {
        if (memberDTO != null) {
            return getMemberIdentifier();
        }
        return memberDTO.getUserName();
    }

    public String getMemberIdentifier(){ return memberDTO.getMemberIdentifier(); }

    public Long getId(){ return memberDTO.getId(); }
}
