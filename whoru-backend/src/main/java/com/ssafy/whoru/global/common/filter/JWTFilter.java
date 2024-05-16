package com.ssafy.whoru.global.common.filter;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.MemberDTO;
import com.ssafy.whoru.global.error.exception.AccessTokenExpiredException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private static String HEADER_AUTHORIZATION = "Authorization";
    private static String TOKEN_PREFIX = "Bearer ";
    private static String[] PERMIT_URL_ARRAY = {
            /* swagger v3 -> authorization */
            "/v3/api-docs/**",
            "/swagger-ui/**",
            /* image */
            "/image/**"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        for(String url : PERMIT_URL_ARRAY) {
            if(request.getRequestURI().contains(url)){
                filterChain.doFilter(request, response);
                return;
            }
        }
        // request에서 토큰 추출 ex) Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
        String token = getAccessToken(request.getHeader(
                HEADER_AUTHORIZATION));

        // 토큰이 없다면 다음 필터로 넘김
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //토큰 검증 ㄱㄱ access 토큰
        try {
           jwtUtil.validateToken(token);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token 만료됨");

            log.error("JWT Token is Expired");
            //response status code
            throw new AccessTokenExpiredException();
        }

        String role = jwtUtil.getRole(token);


        //토큰에서 userId 와 MemberIdentifier 획득
        Long userId = jwtUtil.getUserId(token);
        String memberIdentifier = jwtUtil.getMemberIdentifier(token);

        //Member DTO를 생성하여 값 set
        MemberDTO memberDTO = MemberDTO
                .builder()
                .id(userId)
                .role(role)
                .memberIdentifier(memberIdentifier)
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(memberDTO);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        // jwt 토큰 해더가 있다면 토큰 반환
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        // 없으면 null
        return null;
    }
}
