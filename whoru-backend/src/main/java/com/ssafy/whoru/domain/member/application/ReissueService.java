package com.ssafy.whoru.domain.member.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface ReissueService {

    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);

}
