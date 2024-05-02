package com.ssafy.whoru.domain.message.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Slice;



@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "prev 메세지 목록조회의 DTO")
public class SliceMessageResponse {

    @Schema(description = "다음 목록의 존재여부", pattern = "[ true: 다음 목록이 존재함, false: 다음 목록이 존재하지 않음 ]")
    Boolean hasNext;

    @Schema(description = "prev 메세지 목록조회 결과")
    List<MessageResponse> content;

    public static <E> SliceMessageResponse to(Slice<E> slice, ModelMapper modelMapper){
        return  SliceMessageResponse.builder()
            .hasNext(slice.hasNext())
            .content(
                slice.getContent().stream()
                    .map(entity -> modelMapper.map(entity, MessageResponse.class))
                    .limit(slice.getSize())
                    .collect(Collectors.toList())
            )
            .build();
    }
}
