package com.ssafy.whoru.domain.admin.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminProfileResponse {

    int id;
    String adminName;
    String role;
}
