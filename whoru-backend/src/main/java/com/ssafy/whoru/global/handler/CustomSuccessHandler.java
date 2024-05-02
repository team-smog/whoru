package com.ssafy.whoru.global.handler;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dao.TokenRepository;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.util.JWTUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final RedisUtil redisUtil;

    @Value("${spring.jwt.expire.refresh}")
    private Long time;

    @Value("${spring.local.website}")
    private String url;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        Long userId = customUserDetails.getId();

        // customUserDetails.getId 로 id가 불러와지는지 test====
        if(customUserDetails.getId()!=null){
            log.info("customUserDetails.getId() : "+userId);
        }else{
            log.info("customUserDetails.getId() is null");
        }
        //===================================================
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String accessToken = jwtUtil.createAccessToken(userId,"access", role);
        String refreshToken = jwtUtil.createRefreshToken(userId,"refresh",role);

        log.info("access Token : {}", accessToken);

        //토큰 Redis 저장
//        tokenRepository.saveRefreshToken(customUserDetails.getId(), refreshToken);
        redisUtil.insert(RedisKeyType.REFRESHTOKEN.makeKey(String.valueOf(userId)),refreshToken,time/1000);

        //Response 세팅
        response.addCookie(createCookie("Refresh", refreshToken));
//        response.sendRedirect(local); //로컬
        response.sendRedirect(url + "?accessToken=" + accessToken); // 로컬





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
