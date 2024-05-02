package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dao.TokenRepository;
import com.ssafy.whoru.global.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueServiceImpl implements ReissueService {

    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;

    @Override
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.validateToken(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Long RefreshUserId = jwtUtil.getUserId(refresh);

        //Redis에 저장되어 있는지 확인
        Boolean isExist = tokenRepository.isExist(RefreshUserId,refresh);

        if (!isExist) {
            //response body
            return new ResponseEntity<>("존재하지않는 refresh token", HttpStatus.NOT_FOUND);
        }

        Long userId = jwtUtil.getUserId(refresh);

        //make new JWT
        String newAccess = jwtUtil.createAccessToken(userId,"access", jwtUtil.getRole(refresh));
        String newRefresh = jwtUtil.createRefreshToken(userId,"refresh", jwtUtil.getRole(refresh));

        //Refresh 토큰 저장 Redis에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        tokenRepository.deleteRefreshToken(userId,refresh);
        tokenRepository.saveRefreshToken(userId,refresh);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}
