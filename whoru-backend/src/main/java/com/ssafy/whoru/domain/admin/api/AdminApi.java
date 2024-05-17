

package com.ssafy.whoru.domain.admin.api;

import com.ssafy.whoru.domain.admin.application.AdminService;
import com.ssafy.whoru.domain.admin.dto.request.AdminLoginRequest;
import com.ssafy.whoru.domain.admin.dto.response.AdminTokenResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApi {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<WrapResponse<AdminTokenResponse>> adminLogin(@RequestBody AdminLoginRequest request){
        log.info("Admin login request: {}", request);
        AdminTokenResponse response = adminService.login(request.getId(),request.getPw());
        return ResponseEntity.ok(WrapResponse.create(response,SuccessType.SIMPLE_STATUS));
    }


}
