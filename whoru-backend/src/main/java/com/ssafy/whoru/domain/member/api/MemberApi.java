

package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.member.application.MemberService;
import com.ssafy.whoru.domain.member.application.MemberServiceImpl;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
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

    private final MemberServiceImpl memberService;

    @PatchMapping("/icon")
    public ResponseEntity<WrapResponse<ChangeIconResponse>> changeIcon(@RequestParam("memberId") Long memberId, @RequestParam("iconId") int iconId) {

        ChangeIconResponse response = memberService.changeIcon(memberId, iconId);
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }

    @PostMapping("/logout")
    public ResponseEntity<WrapResponse<Void>> logout(@AuthenticationPrincipal CustomOAuth2User member) {
        memberService.logout(member);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.SIMPLE_STATUS));
    }

    @PostMapping("/regenerate-token")
    public ResponseEntity<WrapResponse<TokenResponse>> regenerateToken(@AuthenticationPrincipal CustomOAuth2User member) {
        log.info("api start");
        log.info(member.toString());
        TokenResponse response = memberService.reGenerateToken(member);
        return ResponseEntity.ok((WrapResponse.create(response,SuccessType.SIMPLE_STATUS)));
    }

}
