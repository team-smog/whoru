package com.ssafy.whoru.global.oauth.service.response;

import java.util.Map;


public class KakaoResponse implements OAuth2Response{

    private final String nickname;
    private final String id;

    public KakaoResponse (Map<String, Object> attribute){
        Map<String,Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        Map<String,Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        this.nickname = profile.get("nickname").toString();
        this.id = attribute.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    //고유번호
    @Override
    public String getProviderId() {
        return id;
    }

    //사용될 닉네임
    @Override
    public String getName() { return nickname; }
}