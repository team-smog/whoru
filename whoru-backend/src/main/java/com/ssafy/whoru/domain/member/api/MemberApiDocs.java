package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Swagger 문서 작업을 위한 추상 인터페이스
 * Controller 코드를 간결화하기 위해 해당 인터페이스에서 어노테이션 작업을 수행
 * **/
@Tag(name ="유저 관련 Controller", description = "유저 관리 API")
public interface MemberApiDocs {

    @PatchMapping("/icon")
    ResponseEntity<WrapResponse<ChangeIconResponse>> changeIcon(@AuthenticationPrincipal CustomOAuth2User member, @RequestParam("iconId") int iconId);

    @PatchMapping("/push-alarm")
    ResponseEntity<WrapResponse<Void>> setPush(@AuthenticationPrincipal CustomOAuth2User member);

    @PostMapping("/logout")
    ResponseEntity<WrapResponse<Void>> logout(@AuthenticationPrincipal CustomOAuth2User member);

    @PostMapping("/regenerate-token")
    ResponseEntity<WrapResponse<TokenResponse>> regenerateToken(@AuthenticationPrincipal CustomOAuth2User member);
}
