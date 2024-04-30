package com.ssafy.whoru.global.oauth;


import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import com.ssafy.whoru.global.oauth.service.ReissueServiceImpl;
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