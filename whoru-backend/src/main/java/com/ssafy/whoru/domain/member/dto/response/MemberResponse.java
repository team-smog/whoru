package com.ssafy.whoru.domain.member.dto.response;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberResponse {

    private Long id;
    private String userName;
    private ProviderType providerType;
    private Icon iconId;
    private int boxCount;
    private LocalDateTime createDate;
    private int reportCount;
    private String refreshToken;
    private String fcmToken;

    public static MemberResponse toDto(Member member){
        return MemberResponse.builder()
                .id(member.getId())
                .userName(member.getUserName())
                .providerType(member.getProvider())
                .iconId(member.getIconId())
                .boxCount(member.getBoxCount())
                .createDate(member.getCreateDate())
                .reportCount(member.getReportCount())
                .refreshToken(member.getRefreshToken())
                .fcmToken(member.getFcmToken())
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .id(getId())
                .userName(getUserName())
                .provider(getProviderType())
                .iconId(getIconId())
                .boxCount(getBoxCount())
                .createDate(getCreateDate())
                .reportCount(getReportCount())
                .refreshToken(getRefreshToken())
                .fcmToken(getFcmToken())
                .build();
    }

}
