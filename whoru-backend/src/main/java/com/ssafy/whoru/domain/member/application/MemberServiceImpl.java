package com.ssafy.whoru.domain.member.application;


import com.ssafy.whoru.domain.collect.application.CrossCollectService;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.ProfileResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.domain.member.exception.*;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.util.JWTUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final FcmService fcmService;
    private final CrossCollectService collectService;

    private final ModelMapper modelMapper;

    private final RedisUtil redisUtil;
    private final JWTUtil jwtUtil;


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
    public void logout(Long memberId, String fcmToken) {
        fcmService.markingUnusedToken(memberId, fcmToken);
        redisUtil.delete(RedisKeyType.REFRESHTOKEN.makeKey(memberId.toString()));
    }

    @Override
    public void setPush(Long memberId) {
        Optional<Member> byId = memberRepository.findById(memberId);
        Member member = byId.orElseThrow(MemberNotFoundException::new);
        List<FcmNotification> fcm = member.getFcmNotifications();
        if (fcm.isEmpty()) {
            throw new FcmNotFoundException();
        }
        fcmService.changeEnabled(member);
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

    @Override
    public ProfileResponse getProfile(Long memberId) {
        Optional<Member> byId = memberRepository.findById(memberId);
        Member member = byId.orElseThrow(MemberNotFoundException::new);
        String url = (member.getIcon()==null) ? "null" : member.getIcon().getIconUrl();
        List<FcmNotification> fcmNotifications = member.getFcmNotifications();
        boolean alarmStatus = true;
        for(FcmNotification fcmNotification: fcmNotifications){
            if(fcmNotification.getMark()) continue; // 삭제 될 예정인 fcm토큰은 신경안써도됨
            if(!fcmNotification.getIsEnabled()){
                alarmStatus = false;
                break;
            }
        }

        return ProfileResponse
                .builder()
                .id(memberId)
                .provider(member.getProvider())
                .memberIdentifier(member.getMemberIdentifier())
                .boxCount(member.getBoxCount())
                .role(member.getRole())
                .createDate(member.getCreateDate())
                .reportCount(member.getReportCount())
                .userName(member.getUserName())
                .languageType(LanguageType.KOREAN)
                .pushAlarm(alarmStatus)
                .iconUrl(url)
                .build();
    }


}
