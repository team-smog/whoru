

package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.member.application.FcmService;
import com.ssafy.whoru.domain.member.application.MemberService;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.ProfileResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.domain.member.exception.RefreshTokenNotFoundException;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApi implements MemberApiDocs {

    private final MemberService memberService;
    private final FcmService fcmService;
    private final MemberRepository memberRepository;

    @PatchMapping("/icon")
    public ResponseEntity<WrapResponse<ChangeIconResponse>> changeIcon(@AuthenticationPrincipal CustomOAuth2User member, @RequestParam("iconId") int iconId) {

        ChangeIconResponse response = memberService.changeIcon(member.getId(), iconId);
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }

    @PatchMapping("/push-alarm")
    public ResponseEntity<WrapResponse<Void>> setPush(@AuthenticationPrincipal CustomOAuth2User member) {
        memberService.setPush(member.getId());
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }

    @GetMapping("/logout")
    public ResponseEntity<WrapResponse<Void>> logout(@AuthenticationPrincipal CustomOAuth2User member, @RequestParam String fcmToken, HttpServletRequest request, HttpServletResponse response) {
        memberService.logout(member.getId(), fcmToken);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Refresh".equals(cookie.getName())) {
                    ResponseCookie expiredCookie = ResponseCookie.from(cookie.getName(), "")
                            .path(cookie.getPath() != null ? cookie.getPath() : "/")
                            .httpOnly(true)
                            .secure(cookie.getSecure())
                            .maxAge(0)
                            .sameSite("Strict")
                            .build();
                    response.addHeader("Set-Cookie", expiredCookie.toString());
                    break;
                }
            }
        }
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }

    @PostMapping("/regenerate-token")
    public ResponseEntity<WrapResponse<TokenResponse>> regenerateToken(@AuthenticationPrincipal CustomOAuth2User member, HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Refresh")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) {
            throw new RefreshTokenNotFoundException();
        }
        TokenResponse response = memberService.reGenerateToken(member.getId(),refreshToken);
        return ResponseEntity.ok((WrapResponse.create(response,SuccessType.SIMPLE_STATUS)));
    }

    @GetMapping("/profile")
    public ResponseEntity<WrapResponse<ProfileResponse>> getProfile(@AuthenticationPrincipal CustomOAuth2User member) {
        ProfileResponse response = memberService.getProfile(member.getId());
        return ResponseEntity.ok(WrapResponse.create(response,SuccessType.SIMPLE_STATUS));
    }

    @PostMapping("/updatefcm")
    public ResponseEntity<WrapResponse<Void>> updateFcm(@AuthenticationPrincipal CustomOAuth2User member, String fcmToken) {
        fcmService.updateFcm(member.getId(),fcmToken);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }



//    @GetMapping("/gettoken")
//    public ResponseEntity<WrapResponse<TokenResponse>> getToken(CustomOAuth2User member) {
//        TokenResponse response = memberService.getToken(member.getId());
//        return ResponseEntity.ok(WrapResponse.create(response,SuccessType.SIMPLE_STATUS));
//    }

}
