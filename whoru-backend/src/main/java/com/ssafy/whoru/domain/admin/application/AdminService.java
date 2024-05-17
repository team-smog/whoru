package com.ssafy.whoru.domain.admin.application;

import com.google.common.base.Optional;
import com.ssafy.whoru.domain.admin.dao.AdminRepository;
import com.ssafy.whoru.domain.admin.domain.Admin;
import com.ssafy.whoru.domain.admin.exception.AdminNotFoundException;
import com.ssafy.whoru.domain.admin.exception.AdminPasswordNotCorrectException;
import com.ssafy.whoru.global.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final JWTUtil jwtUtil;

    public String login(String id, String password) {
        Optional<Admin> admin = adminRepository.findByUserId(id);
        if (!admin.isPresent()) throw new AdminNotFoundException();
        if (admin.get().getPassword().equals(password)) {
            return jwtUtil.createAdminAccessToken(admin.get().getId(),"accessToken","USER_ADMIN");
        }else {
            throw new AdminPasswordNotCorrectException();
        }
    }

}
