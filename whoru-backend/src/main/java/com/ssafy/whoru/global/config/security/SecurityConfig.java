package com.ssafy.whoru.global.config.security;

import com.ssafy.whoru.global.common.filter.JWTFilter;
import com.ssafy.whoru.global.config.security.handler.CustomSuccessHandler;
import com.ssafy.whoru.global.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig{

    private final CustomOAuth2UserServiceImpl customOAuth2UserService; // loaduser로 user entity 관리
    private final CustomSuccessHandler customSuccessHandler;            // 성공시
    private final JWTUtil jwtUtil;              //토큰관련 로직

    private final AccessDeniedHandler accessDeniedHandler;  // 권한이 없는 경우에 대한 handler [403]
    private final AuthenticationEntryPoint authenticationEntryPoint; // 로그인 정보가 충분치 않은 경우에 대한 handler [401]

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf((auth) -> auth.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin((auth)-> auth.disable())
                .httpBasic((auth) -> auth.disable())
                // 토큰 인증 기반이라 세션기반 비활성화
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //경로별 인가 작업`
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        // Prometheus에서 오는 요청만 허용
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login((oauth2) -> oauth2
//                        .loginPage("/login")
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)

                ).exceptionHandling(exceptionConfig ->
                        exceptionConfig
                                .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)

        ;
//                .logout(logout ->
//                        logout.logoutSuccessUrl("/login"))
                 //인증 되지 않은 url 요청시 처리 프로세스

//                .exceptionHandling(exceptionHandling ->
//                        exceptionHandling.defaultAuthenticationEntryPointFor(
//                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
//                                new AntPathRequestMatcher("/api/**")));



        return http.build();
    }

    // cors 설정, Credential true 시 Origin url(client url)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:3000",
                "http://localhost:5173", "http://127.0.0.1:5173", "https://k10d203.p.ssafy.io",
                "https://k10d203.p.ssafy.io:8080" , "https://k10d203.p.ssafy.io:8080/api",
                "https://codearena.shop",
                "https://codearena.shop:8080" , "https://codearena.shop:8080/api")); // 모든 출처 허용
        configuration.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // 허용할 HTTP 메소드 지정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 크레덴셜 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 위 설정 적용
        return source;
    }
}
