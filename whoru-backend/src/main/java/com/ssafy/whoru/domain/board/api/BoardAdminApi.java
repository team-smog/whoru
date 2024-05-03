package com.ssafy.whoru.domain.board.api;


import com.ssafy.whoru.domain.board.application.BoardService;
import com.ssafy.whoru.domain.board.dto.request.PostCommentRequest;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/board")
public class BoardAdminApi {

    private final BoardService boardService;

    @PostMapping("/comment")
    public ResponseEntity<WrapResponse<Void>> postComment(@RequestBody PostCommentRequest request) {

        /**
         * 시큐리티 콘텍스트에서 권한 체크
         * **/
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .filter(role -> role.getAuthority().equals("ROLE_ADMIN"))
                    .findFirst()
                        .orElseThrow(() -> new BusinessLogicException(ErrorCode.FORBIDDEN_ERROR));

        boardService.postComment(request);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }
}
