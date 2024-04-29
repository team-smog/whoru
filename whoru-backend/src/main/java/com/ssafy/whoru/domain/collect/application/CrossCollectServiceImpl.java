package com.ssafy.whoru.domain.collect.application;

import com.ssafy.whoru.domain.collect.dao.IconRepository;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.collect.exception.IconNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrossCollectServiceImpl implements CrossCollectService{

    private final IconRepository iconRepository;

    public Icon findByIdToEntity(Integer iconId) {

        Optional<Icon> icon = iconRepository.findById(iconId);
        return icon.orElseThrow(IconNotFoundException::new);
    }

}
