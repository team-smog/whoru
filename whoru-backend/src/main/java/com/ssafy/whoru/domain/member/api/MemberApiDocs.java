package com.ssafy.whoru.domain.member.api;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.ProfileResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import com.ssafy.whoru.global.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "로그아웃",description = "리프레시 토큰과 FCM 토큰을 받아 해당 FCM토큰을 마킹하고 유저를 로그아웃 합니다.")
    @GetMapping("/logout")
    ResponseEntity<WrapResponse<Void>> logout(@AuthenticationPrincipal CustomOAuth2User member, @RequestParam String fcmToken, HttpServletRequest request, HttpServletResponse response);

    @Operation(summary = "accessToken 재발급 ",description = "토큰을 받아 유저의 accessToken을 재발급합니다.")
    @ApiResponse(responseCode = "200", description = "TokenResponse", content = @Content(schema = @Schema(implementation = TokenResponse.class)))
    @PostMapping("/regenerate-token")
    ResponseEntity<WrapResponse<TokenResponse>> regenerateToken(HttpServletRequest request);


    @Operation(summary = "유저 정보 반환", description = "사용자의 프로필 icon과 닉네임, pushalarm 설정상태, 언어상태를 가져올 수 있다.")
    @ApiResponse(responseCode = "200",description = "fcm 기기명과 iconUrl은 없을시 null 문자열 반환, pushalarm은 default true 입니다. fcmToken은 없으면 빈문자열로 반환 ,언어는 korean 고정상태입니다.(미구현)", content = @Content(schema = @Schema(implementation = ProfileResponse.class)))
    @GetMapping("/profile")
    ResponseEntity<WrapResponse<ProfileResponse>> getProfile(@AuthenticationPrincipal CustomOAuth2User member);

    @Operation(summary = "FCM 기기등록", description = "발급받은 토큰을 통해 DB에 fcm 기기를 등록할 수 있다.")
    @ApiResponse(responseCode = "200")
    @PatchMapping("/updatefcm")
    ResponseEntity<WrapResponse<Void>> updateFcm(@AuthenticationPrincipal CustomOAuth2User member,String fcmToken);

}
