package com.ssafy.whoru.domain.collect.api;

import com.ssafy.whoru.domain.collect.application.CollectService;
import com.ssafy.whoru.domain.collect.dto.response.GetIconResponse;
import com.ssafy.whoru.domain.collect.dto.response.MemberIconResponse;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/collects")
public class CollectApi implements CollectApiDocs {

    private final CollectService collectService;


    @PostMapping("/icons/redeem-random")
    public ResponseEntity<WrapResponse<GetIconResponse>> redeemRandomIcon(@AuthenticationPrincipal CustomOAuth2User member) {

        log.info("request Member -> {}", member.getId());

        GetIconResponse response = collectService.redeemRandomIcon(member.getId());
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.STATUS_201));
    }

    @GetMapping("/icons")
    public ResponseEntity<WrapResponse<MemberIconResponse>> findMemberIcon(@AuthenticationPrincipal CustomOAuth2User member) {

        log.info("request Member -> {}", member.getId());

        MemberIconResponse response = collectService.findMemberIcon(member.getId());
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));

    }

}
