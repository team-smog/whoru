package com.ssafy.whoru.domain.collect.application;

import com.ssafy.whoru.domain.collect.dao.IconRepository;
import com.ssafy.whoru.domain.collect.dao.MemberIconRepository;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.collect.domain.MemberIcon;
import com.ssafy.whoru.domain.collect.exception.IconNotFoundException;
import com.ssafy.whoru.domain.collect.exception.MemberNotPossessionException;
import com.ssafy.whoru.domain.member.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrossCollectServiceImpl implements CrossCollectService{

    private final IconRepository iconRepository;

    private final MemberIconRepository memberIconRepository;

    @Override
    public Icon findByIdToEntity(Integer iconId) {

        Optional<Icon> icon = iconRepository.findById(iconId);
        return icon.orElseThrow(IconNotFoundException::new);
    }

    public MemberIcon findByMemberAndIcon(Member member, Integer iconId) {

        Icon icon = iconRepository.findById(iconId)
                .orElseThrow(IconNotFoundException::new);

        return memberIconRepository.findByMemberANDIcon(member, icon)
            .orElseThrow(MemberNotPossessionException::new);
    }

}
