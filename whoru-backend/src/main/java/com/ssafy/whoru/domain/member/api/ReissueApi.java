package com.ssafy.whoru.domain.member.api;


import com.ssafy.whoru.domain.member.application.ReissueServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/auth")
@RequiredArgsConstructor
@ResponseBody
public class ReissueApi implements ReissueApiDocs{

    private final ReissueServiceImpl reissueService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissue(request,response);
    }


}