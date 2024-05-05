package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Author 정민호
 * */

/**
 * Swagger 문서 작업을 위한 추상 인터페이스
 * Controller 코드를 간결화하기 위해 해당 인터페이스에서 어노테이션 작업을 수행
 * **/
@Tag(name ="유저 API ", description = "유저 관리 API")
public interface MemberApiDocs {

    @Operation(summary = "Icon 변경",description = "토큰과 함께 iconId를 받아 유저의 아이콘을 변경합니다.")
    @Parameter(name = "IconId", required = true,description = "변경할 icon의 id 입니다.")
    @ApiResponse(responseCode = "200", description = "ChangeIconResponse", content = @Content(schema = @Schema(implementation = ChangeIconResponse.class)))
    @PatchMapping("/icon")
    ResponseEntity<WrapResponse<ChangeIconResponse>> changeIcon(@AuthenticationPrincipal CustomOAuth2User member, @RequestParam("iconId") int iconId);

    @Operation(summary = "푸시알람 설정",description = "토큰을 받아 유저의 푸시알람 설정을 변경합니다.")
    @PatchMapping("/push-alarm")
    ResponseEntity<WrapResponse<Void>> setPush(@AuthenticationPrincipal CustomOAuth2User member);

    @Operation(summary = "로그아웃",description = "토큰을 받아 유저를 로그아웃 합니다.")
    @PostMapping("/logout")
    ResponseEntity<WrapResponse<Void>> logout(@AuthenticationPrincipal CustomOAuth2User member);

    @Operation(summary = "accessToken 재발급 ",description = "토큰을 받아 유저의 accessToken을 재발급합니다.")
    @ApiResponse(responseCode = "200", description = "TokenResponse", content = @Content(schema = @Schema(implementation = TokenResponse.class)))
    @PostMapping("/regenerate-token")
    ResponseEntity<WrapResponse<TokenResponse>> regenerateToken(@AuthenticationPrincipal CustomOAuth2User member);
}
