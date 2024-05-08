package com.ssafy.whoru.domain.member.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Long id;
    private String userName;
    private String memberIdentifier;
    private String role;

}
