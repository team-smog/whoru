package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.collect.dto.response.GetIconResponse;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.ProfileResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.profiles.Profile;

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

    @Operation(summary = "유저 정보 반환", description = "사용자의 프로필 icon과 닉네임, pushalarm 설정상태, 언어상태, fcm 기기명을 가져올 수 있다.")
    @ApiResponse(responseCode = "200",description = "fcm 기기명과 iconUrl은 없을시 null 문자열 반환, pushalarm은 default true 입니다. 언어는 korean 고정상태입니다.(미구현)", content = @Content(schema = @Schema(implementation = ProfileResponse.class)))
    @GetMapping("/profile")
    ResponseEntity<WrapResponse<ProfileResponse>> getProfile(@AuthenticationPrincipal CustomOAuth2User member);
}
