

package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.member.application.FcmService;
import com.ssafy.whoru.domain.member.application.MemberService;
import com.ssafy.whoru.domain.member.dao.FcmRepository;
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

    @PostMapping("/logout")
    public ResponseEntity<WrapResponse<Void>> logout(@AuthenticationPrincipal CustomOAuth2User member, HttpServletRequest request, HttpServletResponse response) {
        memberService.logout(member.getId());
        Cookie[] cookies = request.getCookies();
        Cookie myCookie;
        for(Cookie c : cookies){
            if(c.getName().equals("Refresh")){
                myCookie = c;
                myCookie.setHttpOnly(true);
                myCookie.setPath("/");
                myCookie.setMaxAge(0);
                response.addCookie(myCookie);
                break;
            }
        }
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }

    @PostMapping("/regenerate-token")
    public ResponseEntity<WrapResponse<TokenResponse>> regenerateToken(@AuthenticationPrincipal CustomOAuth2User member,
                                                                       HttpServletRequest request) {
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

    @PostMapping("/fcmregistration")
    public ResponseEntity<WrapResponse<Void>> create(CustomOAuth2User member, String fcmToken) {
        fcmService.registrationFcm(fcmToken);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }



}
