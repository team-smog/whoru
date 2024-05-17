package com.ssafy.whoru.domain.admin.application;

import com.ssafy.whoru.domain.admin.dao.AdminRepository;
import com.ssafy.whoru.domain.admin.domain.Admin;
import com.ssafy.whoru.domain.admin.dto.response.AdminTokenResponse;
import com.ssafy.whoru.domain.admin.exception.AdminNotFoundException;
import com.ssafy.whoru.global.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final JWTUtil jwtUtil;

    public AdminTokenResponse login(String id, String password) {
        Optional<Admin> admin = Optional.ofNullable(adminRepository.findByUserId(id));
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return AdminTokenResponse
                    .builder()
                    .name(admin.get().getName())
                    .token(jwtUtil.createAccessToken(
                            admin.get().getId(),"accessToken","USER_ADMIN"))
                    .build();

        }else {
            throw new AdminNotFoundException();
        }
    }

}
