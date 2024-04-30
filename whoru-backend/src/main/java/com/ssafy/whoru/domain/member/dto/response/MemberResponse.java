package com.ssafy.whoru.domain.member.dto.response;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberResponse {

    private Long id;
    private String userName;
    private String memberIdentifier;
    private ProviderType providerType;
    private Icon iconId;
    private int boxCount;
    private LocalDateTime createDate;
    private int reportCount;
    private String refreshToken;
    private LanguageType languageType;
    private FcmNotification fcmNotification;
    public static MemberResponse toDto(Member member){
        return MemberResponse.builder()
                .id(member.getId())
                .userName(member.getUserName())
                .memberIdentifier(member.getMemberIdentifier())
                .providerType(member.getProvider())
                .iconId(member.getIconId())
                .boxCount(member.getBoxCount())
                .createDate(member.getCreateDate())
                .reportCount(member.getReportCount())
                .refreshToken(member.getRefreshToken())
                .languageType(member.getLanguageType())
                .fcmNotification(member.getFcmNotification())
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .id(getId())
                .userName(getUserName())
                .memberIdentifier(getMemberIdentifier())
                .provider(getProviderType())
                .iconId(getIconId())
                .boxCount(getBoxCount())
                .createDate(getCreateDate())
                .reportCount(getReportCount())
                .refreshToken(getRefreshToken())
                .languageType(getLanguageType())
                .fcmNotification(getFcmNotification())
                .build();
    }

}
