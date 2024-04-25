package com.ssafy.whoru.domain.collect.api;

import com.ssafy.whoru.domain.collect.dto.response.GetIconResponse;
import com.ssafy.whoru.domain.collect.dto.response.MemberIconResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Swagger 문서 작업을 위한 추상 인터페이스
 * Controller 코드를 간결화하기 위해 해당 인터페이스에서 어노테이션 작업을 수행
 * **/
@Tag(name ="수집 컨텐츠 관련 Controller", description = "수집 컨텐츠 API")
public interface CollectApiDocs {

    @Operation(summary = "랜덤 박스 오픈", description = "사용자가 소유한 박스를 열어 캐릭터 아이콘을 확률에 의해 획득할 수 있다.")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = GetIconResponse.class)))
    public ResponseEntity<WrapResponse<GetIconResponse>> redeemRandomIcon(@PathVariable("memberId") Long userId);

    @Operation(summary = "사용자 아이콘 조회", description = "사용자가 소유한 아이콘을 비롯한 전체 아이콘의 데이터와 사용자가 소유한지 여부에 대한 응답")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MemberIconResponse.class)))
    public ResponseEntity<WrapResponse<MemberIconResponse>> findMemberIcon(@PathVariable("memberId") Long memberId);
}
