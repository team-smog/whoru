package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.member.application.MemberService;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApi implements MemberApiDocs {

    private final MemberService memberService;

    @PatchMapping("/icon")
    public ResponseEntity<WrapResponse<ChangeIconResponse>> changeIcon(@RequestParam("memberId") Long memberId, @RequestParam("iconId") int iconId) {

        ChangeIconResponse response = memberService.changeIcon(memberId, iconId);
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }

    @PostMapping("/logout")
    public ResponseEntity<WrapResponse<ChangeIconResponse>> logout(@AuthenticationPrincipal Member member) {

        ChangeIconResponse response = memberService.logout();
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }
}
