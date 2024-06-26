package com.ssafy.whoru.global.config.security.handler;

import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.util.JWTUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
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

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();


        String accessToken = jwtUtil.createAccessToken(userId,"access", role);

        String refreshToken;
        //기존 Redis에 저장된 Refresh가 있다면 해당값을 전달
        Optional<String> redisRefreshTokenOpt = redisUtil.findValueByKey(RedisKeyType.REFRESHTOKEN.makeKey(String.valueOf(userId)));

        log.info("Redis search value -> {}", redisRefreshTokenOpt);
        if(redisRefreshTokenOpt.isPresent()) {
            refreshToken = redisRefreshTokenOpt.get();
        }
        else {
            refreshToken = jwtUtil.createRefreshToken(userId,"refresh",role);
            redisUtil.insert(RedisKeyType.REFRESHTOKEN.makeKey(String.valueOf(userId)),refreshToken,time/1000);
        }


        log.info("access Token : {}", accessToken);

        log.info("request endpoint : {}", request.getRequestURI());
        //기존 Redis에 저장된 Refresh가 있다면 해당값을 전달
        //토큰 Redis 저장

        //Response 세팅
        ResponseCookie refreshCookie = ResponseCookie.from("Refresh", refreshToken)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        response.addHeader("Set-Cookie", refreshCookie.toString());

        response.sendRedirect(url +"callback" + "?accessToken=" + accessToken);
//        response.sendRedirect(testUrl +"callback" + "?accessToken=" + accessToken);
    }
}
