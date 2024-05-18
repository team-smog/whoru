

package com.ssafy.whoru.domain.admin.api;

import com.ssafy.whoru.domain.admin.application.AdminService;
import com.ssafy.whoru.domain.admin.dto.request.AdminLoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApi {

    private final AdminService adminService;

    @Value("${spring.local.website}")
    private String url;

    @PostMapping("/login")
    public void adminLogin(@RequestBody AdminLoginRequest info, HttpServletResponse response) throws IOException {
        log.info("request body -> id: {}, pw: {}", info.getId(), info.getPw());
        String token = adminService.login(info.getId(),info.getPw());

        response.addHeader("Authorization", token);
    }
}
