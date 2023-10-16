package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppMultipleChoiceAnswer;
import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.service.dto.AppMultipleChoiceAnswerDTO;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppMultipleChoiceAnswer} and its DTO {@link AppMultipleChoiceAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppMultipleChoiceAnswerMapper extends EntityMapper<AppMultipleChoiceAnswerDTO, AppMultipleChoiceAnswer> {
    @Mapping(target = "question", source = "question", qualifiedByName = "appQuestionId")
    AppMultipleChoiceAnswerDTO toDto(AppMultipleChoiceAnswer s);

    @Named("appQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppQuestionDTO toDtoAppQuestionId(AppQuestion appQuestion);
}
