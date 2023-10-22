package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppMultipleChoiceAnswer;
import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.domain.AppQuestionVideoInfo;
import com.auth0.rainbow.service.dto.AppMultipleChoiceAnswerDTO;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import com.auth0.rainbow.service.dto.AppQuestionVideoInfoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppQuestion} and its DTO {@link AppQuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppQuestionMapper extends EntityMapper<AppQuestionDTO, AppQuestion> {
    AppQuestionMapper INSTANCE = Mappers.getMapper(AppQuestionMapper.class);
    AppMultipleChoiceAnswerMapper oMultipleChoiceAnswerMapper = Mappers.getMapper(AppMultipleChoiceAnswerMapper.class);
    AppQuestionVideoInfoMapper othQuestionVideoInfoMapper = Mappers.getMapper(AppQuestionVideoInfoMapper.class);

    @Named("toChoiceDTO")
    @Mappings(
        {
            @Mapping(target = "multiChoice", source = "questions", qualifiedByName = "mapToMultiChoiceSet"),
            @Mapping(target = "appQuestionvideo", source = "appQuestion", qualifiedByName = "mapToVideoSet"),
        }
    )
    AppQuestionDTO toChoiceDTO(AppQuestion appQuestion);

    @Named("mapToMultiChoiceSet")
    static Set<AppMultipleChoiceAnswerDTO> mapToMultiChoiceSet(Set<AppMultipleChoiceAnswer> multi) {
        return multi
            .stream()
            .map(multiChoice -> {
                if (multiChoice == null) {
                    return null;
                }
                multiChoice.setQuestion(null);
                return AppMultipleChoiceAnswerMapper.INSTANCE.toDto(multiChoice);
            })
            .collect(Collectors.toSet());
    }

    @Named("mapToVideoSet")
    static AppQuestionVideoInfoDTO mapToVideoSet(AppQuestionVideoInfo appQuestionVideoInfo) {
        if (appQuestionVideoInfo == null) {
            return null;
        }
        appQuestionVideoInfo.setAppQuestion(null);
        return AppQuestionVideoInfoMapper.INSTANCE.toDto(appQuestionVideoInfo);
    }
}
