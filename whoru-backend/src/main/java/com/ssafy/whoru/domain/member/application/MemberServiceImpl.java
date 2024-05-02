package com.ssafy.whoru.domain.member.application;


import com.ssafy.whoru.domain.collect.application.CrossCollectService;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.exception.MemberAlreadyIconException;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final CrossCollectService collectService;

    private final ModelMapper modelMapper;

    private final RedisUtil redisUtil;

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
    public void logout(Member member) {
        redisUtil.delete(RedisKeyType.REFRESHTOKEN.makeKey(member.getId().toString()));
    }
}
