

package com.ssafy.whoru.domain.admin.api;

import com.ssafy.whoru.domain.admin.application.AdminService;
import com.ssafy.whoru.domain.admin.dto.request.AdminLoginRequest;
import com.ssafy.whoru.domain.admin.dto.response.AdminProfileResponse;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApi {

    private final AdminService adminServiceImpl;

    @Value("${spring.local.website}")
    private String url;

    @PostMapping("/login")
    public void adminLogin(@RequestBody AdminLoginRequest info, HttpServletResponse response) throws IOException {
        log.info("request body -> id: {}, pw: {}", info.getId(), info.getPw());
        String token = adminServiceImpl.login(info.getId(),info.getPw());
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", token);
    }

    @GetMapping("/profile")
    public ResponseEntity<WrapResponse<AdminProfileResponse>> getProfile(@AuthenticationPrincipal CustomOAuth2User member) {
        log.info("request member -> {}",member.getId());
        AdminProfileResponse response = adminServiceImpl.getProfile(member.getId().intValue());
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }
}
