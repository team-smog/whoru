package com.ssafy.whoru.global.oauth;

import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.global.oauth.service.response.KakaoResponse;
import com.ssafy.whoru.global.oauth.service.response.OAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService implements CustomOAuth2UserService{

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("loadUser start");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User : {}", oAuth2User.toString());


        System.out.println(oAuth2User);

        // 어느 플랫폼의 소셜로그인인지 알아야함
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //
        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("kakao")) {
            log.info("kakao resource accept");
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }
//        else if (registrationId.equals("naver")) {
//
//            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
//        }
//        else if (registrationId.equals("google")) {
//
//            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
//        }
        else {

            return null;
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String memberIdentifier = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        String name = oAuth2Response.getName();

        //DB에 해당 유저 존재하는지 확인
        Optional<Member> existData = memberRepository.findByUserName(memberIdentifier);
        log.info("DB 저장로직 수행 완료");

        //없으면 회원가입 시켜주고 저장
        if (existData.isEmpty()){
            log.info("회원가입 로직 수행");
            Member member =  Member
                    .builder()
                    .userName(name)
                    .memberIdentifier(memberIdentifier)
                    .build();


            Member save = memberRepository.save(member);

            MemberDTO memberDTO = MemberDTO
                    .builder()
                    .memberIdentifier(memberIdentifier)
                    .userName(name)
                    .id(save.getId())
                    .build();


            return new CustomOAuth2User(memberDTO);

        }else{   //있으면 패스
            log.info("로그인 로직 수행");
            MemberDTO memberDTO = MemberDTO
                    .builder()
                    .id(existData.get().getId())
                    .build();

            return new CustomOAuth2User(memberDTO);
        }
    }
}
