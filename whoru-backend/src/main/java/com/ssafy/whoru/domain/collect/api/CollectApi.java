package com.ssafy.whoru.domain.collect.api;

import com.ssafy.whoru.domain.collect.application.CollectService;
import com.ssafy.whoru.domain.collect.dto.response.GetIconResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collects")
public class CollectApi implements CollectApiDocs {

    private final CollectService collectService;


    @PostMapping("/{memberId}/icons/redeem-random")
    public ResponseEntity<WrapResponse<GetIconResponse>> redeemRandomIcon(@PathVariable Long memberId) {

        GetIconResponse response = collectService.redeemRandomIcon(memberId);
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.STATUS_201));
    }

}
