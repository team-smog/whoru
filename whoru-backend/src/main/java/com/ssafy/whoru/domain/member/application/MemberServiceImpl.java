package com.ssafy.whoru.domain.member.application;


import com.ssafy.whoru.domain.collect.application.CrossCollectService;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.domain.member.exception.*;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.error.exception.SimpleException;
import com.ssafy.whoru.global.util.JWTUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Optional;

import static com.ssafy.whoru.domain.member.domain.QMember.member;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final CrossMemberService crossMemberService;
    private final MemberRepository memberRepository;

    private final FcmService fcmService;
    private final CrossCollectService collectService;

    private final ModelMapper modelMapper;

    private final RedisUtil redisUtil;
    private final JWTUtil jwtUtil;
    private final FcmServiceImpl fcmServiceImpl;


    @Override
    @Transactional
    public ChangeIconResponse changeIcon(Long memberId, int iconId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.MEMBER_NOT_FOUND));

        //Icon 조회
        Icon icon = collectService.findByIdToEntity(iconId);

        //현재 적용된 아이콘과 동일한지 조회
        if (member.getIcon() != null && member.getIcon().hashCode() == icon.hashCode()) {
            throw new MemberAlreadyIconException();
        }

        //현재 유저가 해당하는 Icon을 가지고 있는지 조회
        collectService.findByMemberAndIcon(member, iconId);

        //현재 유저의 아이콘 변경
        member.updateIcon(icon);

        return modelMapper.map(icon, ChangeIconResponse.class);
    }

    @Override
    public void logout(Long memberId) {
        log.info("here");
        redisUtil.delete(RedisKeyType.REFRESHTOKEN.makeKey(memberId.toString()));
        log.info("logout");
    }

    @Override
    @Transactional
    public void setPush(Long memberId) {
        Member byId = crossMemberService.findByIdToEntity(memberId);
        FcmNotification fcm = byId.getFcmNotification();
        if (fcm == null) {
            throw new FcmNotFoundException();
        }
        fcmService.changeIsEnabled(fcm);
    }

    @Override
    public TokenResponse reGenerateToken(Long memberId,String refresh) {
        Optional<String> valueByKey = redisUtil.findValueByKey(RedisKeyType.REFRESHTOKEN.makeKey(String.valueOf(memberId)));

        if (valueByKey.isEmpty()||!jwtUtil.validateToken(valueByKey.get())){
            redisUtil.delete(RedisKeyType.REFRESHTOKEN.makeKey(memberId.toString()));
            throw new RefreshTokenNotFoundException();
        }
        if(valueByKey.get().equals(refresh)) {
            String createdAccessToken = jwtUtil.createAccessToken(memberId, "access", "ROLE_USER");
            return TokenResponse
                    .builder()
                    .token(createdAccessToken)
                    .build();
        }
        throw new RefreshTokenExpiredException();
    }

}
