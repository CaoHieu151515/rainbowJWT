package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppQuestion;
import com.auth0.rainbow.domain.AppQuestionVideoInfo;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import com.auth0.rainbow.service.dto.AppQuestionVideoInfoDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppQuestionVideoInfo} and its DTO {@link AppQuestionVideoInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppQuestionVideoInfoMapper extends EntityMapper<AppQuestionVideoInfoDTO, AppQuestionVideoInfo> {
    AppQuestionVideoInfoMapper INSTANCE = Mappers.getMapper(AppQuestionVideoInfoMapper.class);

    @Mapping(target = "appQuestion", source = "appQuestion", qualifiedByName = "appQuestionId")
    AppQuestionVideoInfoDTO toDto(AppQuestionVideoInfo s);

    @Named("appQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppQuestionDTO toDtoAppQuestionId(AppQuestion appQuestion);
}
