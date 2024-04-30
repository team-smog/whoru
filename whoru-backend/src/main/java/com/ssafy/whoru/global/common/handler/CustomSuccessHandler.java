package com.ssafy.whoru.global.common.handler;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dao.TokenRepository;
import com.ssafy.whoru.global.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        // customUserDetails.getId 로 id가 불러와지는지 test====
        if(customUserDetails.getId()!=null){
            log.info("customUserDetails.getId() : "+customUserDetails.getId());
        }else{
            log.info("customUserDetails.getId() is null");
        }
        //===================================================

        String accessToken = jwtUtil.createAccessToken(customUserDetails.getId(),"access");
        String refreshToken = jwtUtil.createRefreshToken(customUserDetails.getId(),"refresh");

        //토큰 Redis 저장
        tokenRepository.saveRefreshToken(customUserDetails.getId(), refreshToken);

        //Response 세팅
        response.setHeader("Authorization", accessToken);
        response.addCookie(createCookie("Refresh", refreshToken));
        response.sendRedirect("http://localhost:8080/index.html");




    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60*60);
        //cookie.setSecure(true);  https
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
