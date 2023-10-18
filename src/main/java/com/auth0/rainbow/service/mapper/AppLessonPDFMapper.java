package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppLessonInfo;
import com.auth0.rainbow.domain.AppLessonPDF;
import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import com.auth0.rainbow.service.dto.AppLessonPDFDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppLessonPDF} and its DTO {@link AppLessonPDFDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppLessonPDFMapper extends EntityMapper<AppLessonPDFDTO, AppLessonPDF> {
    @Mapping(target = "lesson", source = "lesson", qualifiedByName = "appLessonInfoId")
    AppLessonPDFDTO toDto(AppLessonPDF s);

    @Named("appLessonInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppLessonInfoDTO toDtoAppLessonInfoId(AppLessonInfo appLessonInfo);
}
