package com.ssafy.whoru.global.handler;

import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.util.JWTUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
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
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;

    @Value("${spring.jwt.expire.refresh}")
    private Long time;

    @Value("${spring.local.website}")
    private String url;

    @Value(("${spring.test.url}"))
    private String testUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        Long userId = customUserDetails.getId();
        String username = memberRepository.findById(userId).get().getUserName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();


        String accessToken = jwtUtil.createAccessToken(userId,"access", role);
        String refreshToken = jwtUtil.createRefreshToken(userId,"refresh",role);

        log.info("access Token : {}", accessToken);

        //토큰 Redis 저장
        redisUtil.insert(RedisKeyType.REFRESHTOKEN.makeKey(String.valueOf(userId)),refreshToken,time/1000);

        //Response 세팅
        ResponseCookie refreshCookie = ResponseCookie.from("Refresh", refreshToken)
                .maxAge(60 * 60 * 60 * 60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        response.addHeader("Set-Cookie", refreshCookie.toString());

        response.sendRedirect(url +"callback" + "?accessToken=" + accessToken);
//        response.sendRedirect(testUrl +"callback" + "?accessToken=" + accessToken);
    }
}
