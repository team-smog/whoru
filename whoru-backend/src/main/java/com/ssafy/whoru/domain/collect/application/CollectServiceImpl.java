package com.ssafy.whoru.domain.collect.application;

import com.ssafy.whoru.domain.collect.dao.IconRepository;
import com.ssafy.whoru.domain.collect.dao.MemberIconRepository;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.collect.domain.MemberIcon;
import com.ssafy.whoru.domain.collect.dto.IconGradeType;
import com.ssafy.whoru.domain.collect.dto.response.GetIconResponse;
import com.ssafy.whoru.domain.collect.exception.BoxCountInvalidException;
import com.ssafy.whoru.domain.collect.exception.IconNotFoundException;
import com.ssafy.whoru.domain.member.application.CrossMemberService;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.application.MemberService;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectServiceImpl implements CollectService {

    private static final int RANDOM_BOUND = 100;    //난수 범위값

    private final CrossMemberService memberService;

    private final IconRepository IconRepository;

    private final MemberIconRepository memberIconRepository;

    private final ModelMapper modelMapper;

    @Override
    public GetIconResponse redeemRandomIcon(Long userId) {

        //유저Id를 통해 해당 유저가 랜덤박스를 1개 이상 소지하고 있는지 확인.
        Member member = memberService.findByIdToEntity(userId);

        //박스 개수가 부족할 경우 예외처리
        if(member.getBoxCount() == 0) throw new BoxCountInvalidException(ErrorCode.COLLECT_VALUE_INVALID);

        /**
         * 0 ~ 1 사이의 Random 난수 생성 후 맞는 등급의 아이템 탐색
         * **/

        int randomValue = ThreadLocalRandom.current().nextInt(RANDOM_BOUND);

        IconGradeType grade = null;
        for(IconGradeType item : IconGradeType.values()) {
            if( randomValue <= item.getProbability() ) {
                grade = item;
                break;
            }
        }

        if(grade == null) throw new BoxCountInvalidException(ErrorCode.COLLECT_RANDOM_ERROR);
        //해당하는 등급에 맞는 랜덤 아이템 탐색

        Optional<Icon> icon = Optional.ofNullable(IconRepository.findByRandomIcon(grade)
            .orElseThrow(() -> new IconNotFoundException(ErrorCode.ICON_NOT_FOUND)));

        GetIconResponse response = modelMapper.map(icon, GetIconResponse.class);

        //현재 사용자의 박스 카운트 1 감소
        member.updateBoxDecrease();

        //사용자 보유 아이콘에 해당 아이콘이 있는지 확인 후 없다면 갱신
        Optional<MemberIcon> result = memberIconRepository.findByMemberANDIcon(member, icon.get());

        if(result.isPresent()) {
            response.setIsDuplicat(true);
        }
        else {
            //갱신
            response.setIsDuplicat(false);
            memberIconRepository.save(MemberIcon.builder()
                .member(member)
                .icon(icon.get())
                .build());
        }


        return response;
    }


}
