package com.ssafy.whoru.domain.admin.application;

import com.ssafy.whoru.domain.admin.dto.response.AdminProfileResponse;

public interface AdminService {

    public String login(String id, String password);

    public AdminProfileResponse getProfile(int memberId);
}
