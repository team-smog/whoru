

package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.member.application.FcmService;
import com.ssafy.whoru.domain.member.application.MemberService;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.ProfileResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.domain.member.exception.RefreshTokenNotFoundException;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import com.ssafy.whoru.global.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApi implements MemberApiDocs{

    private final MemberService memberService;
    private final FcmService fcmService;
    private final JWTUtil jwtUtil;

    @PatchMapping("/icon")
    public ResponseEntity<WrapResponse<ChangeIconResponse>> changeIcon(@AuthenticationPrincipal CustomOAuth2User member, @RequestParam("iconId") int iconId) {

        log.info("request Member -> {}", member.getId());

        log.info("request param -> {}", iconId);

        ChangeIconResponse response = memberService.changeIcon(member.getId(), iconId);
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }

    @PatchMapping("/push-alarm")
    public ResponseEntity<WrapResponse<Void>> setPush(@AuthenticationPrincipal CustomOAuth2User member) {
        log.info("request member -> {}",member.getId());
        memberService.setPush(member.getId());
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }

    @GetMapping("/logout")
    public ResponseEntity<WrapResponse<Void>> logout(@AuthenticationPrincipal CustomOAuth2User member, @RequestParam String fcmToken, HttpServletRequest request, HttpServletResponse response) {
        log.info("request member -> {}",member.getId());
        log.info("request param -> fcmToken: {}",fcmToken);
        log.info("request -> cookies: {}", Arrays.toString(request.getCookies()));

        memberService.logout(member.getId(), fcmToken);
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info(cookie.getName());
                if ("Refresh".equals(cookie.getName())) {
                    ResponseCookie expiredCookie = ResponseCookie.from(cookie.getName(), null)
                            .path("/")
                            .maxAge(0)
                            .secure(true)
                            .sameSite("None")
                            .httpOnly(true)
                            .build();
                    response.addHeader("Set-Cookie", expiredCookie.toString());
                    break;
                }
            }
        }
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }

    @PostMapping("/regenerate-token")
    public ResponseEntity<WrapResponse<TokenResponse>> regenerateToken(HttpServletRequest request) {

        log.info("request -> cookies: {}", Arrays.toString(request.getCookies()));
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies)
                .filter(cookie -> "Refresh".equals(cookie.getName()))
                .findFirst();

            if (refreshTokenCookie.isPresent()) {
                refreshToken = refreshTokenCookie.get().getValue();
            }
        }
        if (refreshToken == null) {
            throw new RefreshTokenNotFoundException();
        }

        // 토큰을 통해 유저 ID 가져오기
        Long memberId = jwtUtil.getMemberId(refreshToken);

        TokenResponse response = memberService.reGenerateToken(memberId, refreshToken);
        return ResponseEntity.ok((WrapResponse.create(response,SuccessType.SIMPLE_STATUS)));
    }

    @GetMapping("/profile")
    public ResponseEntity<WrapResponse<ProfileResponse>> getProfile(@AuthenticationPrincipal CustomOAuth2User member) {
        log.info("request member -> {}",member.getId());
        ProfileResponse response = memberService.getProfile(member.getId());
        return ResponseEntity.ok(WrapResponse.create(response,SuccessType.SIMPLE_STATUS));
    }

    @PostMapping("/updatefcm")
    public ResponseEntity<WrapResponse<Void>> updateFcm(@AuthenticationPrincipal CustomOAuth2User member, String fcmToken) {
        log.info("request member -> {}",member.getId());
        log.info("request param -> fcmToken: {}",fcmToken);
        fcmService.updateFcm(member.getId(),fcmToken);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }


}
