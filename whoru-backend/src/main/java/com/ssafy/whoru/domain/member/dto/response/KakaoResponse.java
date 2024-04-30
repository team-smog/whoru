package com.ssafy.whoru.domain.member.dto.response;

import lombok.RequiredArgsConstructor;

import java.util.Map;


public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public KakaoResponse (Map<String, Object> attribute){
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    //고유번호
    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    //사용될 닉네임
    @Override
    public String getName() {

        return attribute.get("kakao_account")toString();
    }
}