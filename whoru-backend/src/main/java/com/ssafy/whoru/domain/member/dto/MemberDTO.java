package com.ssafy.whoru.domain.member.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Long member_id;
    private String name;
    private String userName;
}
