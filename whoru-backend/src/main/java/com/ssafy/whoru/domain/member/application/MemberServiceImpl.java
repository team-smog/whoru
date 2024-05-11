package com.ssafy.whoru.domain.member.application;


import com.ssafy.whoru.domain.collect.application.CrossCollectService;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.ProfileResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;
import com.ssafy.whoru.domain.member.exception.*;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.error.exception.SimpleException;
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

    private final CrossMemberService crossMemberService;
    private final MemberRepository memberRepository;

    private final CrossFcmService crossFcmService;
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
        log.info("fcm before");
        crossFcmService.markingUnusedToken(memberId, fcmToken);
        log.info("fcm after");
        redisUtil.delete(RedisKeyType.REFRESHTOKEN.makeKey(memberId.toString()));
    }

    @Override
//    @Transactional
    public void setPush(Long memberId) {
        Member byId = crossMemberService.findByIdToEntity(memberId);
        List<FcmNotification> fcm = byId.getFcmNotifications();
        if (fcm.isEmpty()) {
            throw new FcmNotFoundException();
        }
        crossFcmService.changeEnabled(byId);
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
        Member byId = crossMemberService.findByIdToEntity(memberId);

        String url = (byId.getIcon()==null) ? "null" : byId.getIcon().getIconUrl();
        List<FcmNotification> fcmNotifications = byId.getFcmNotifications();
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
                .provider(byId.getProvider())
                .memberIdentifier(byId.getMemberIdentifier())
                .boxCount(byId.getBoxCount())
                .role(byId.getRole())
                .createDate(byId.getCreateDate())
                .reportCount(byId.getReportCount())
                .userName(byId.getUserName())
                .languageType(LanguageType.KOREAN)
                .pushAlarm(alarmStatus)
                .iconUrl(url)
                .build();
    }

    // Get FCM Token을 할 필요가 없음
    // 사유: 이제 해당 회원이 발급받는 모든 FCM Token은 관리 아래에 둠
    // 그리고 사용하지 않는 토큰은 주기적으로 삭제함

//    @Override
//    public TokenResponse getToken(Long id) {
//        Optional<Member> byId = Optional.ofNullable(memberRepository.findById(id)
//                .orElseThrow(FcmTokenNotFoundException::new));
//        if(byId.get().getFcmNotification()== null){
//            throw new FcmNotFoundException();
//        }
//        return TokenResponse
//                .builder()
//                .token(byId.get().getFcmNotification().getFcmToken())
//                .username(byId.get().getUserName())
//                .build();
//        return null;
//    }


}
