package com.ssafy.whoru.global.oauth;


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

}
