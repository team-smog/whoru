package com.ssafy.whoru.domain.member.dto.response;

import java.util.Map;


public class KakaoResponse implements OAuth2Response{

    private final  Map<String, Object> attribute;
    private final  Map<String,Object> kakaoAccount;
    private final  Map<String,Object> profile;


    public KakaoResponse (Map<String, Object> attribute){
        this.attribute = attribute;
        this.kakaoAccount = (Map<String, Object>) this.attribute.get("kakao_account");
        this.profile = (Map<String, Object>) kakaoAccount.get("profile");
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
    public String getName() { return profile.get("nickname").toString(); }
}