package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppPostImage;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.dto.AppPostImageDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppPostImage} and its DTO {@link AppPostImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppPostImageMapper extends EntityMapper<AppPostImageDTO, AppPostImage> {
    AppPostImageMapper INSTANCE = Mappers.getMapper(AppPostImageMapper.class);

    @Mapping(target = "post", source = "post", qualifiedByName = "appPostId")
    AppPostImageDTO toDto(AppPostImage s);

    @Named("appPostId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppPostDTO toDtoAppPostId(AppPost appPost);
}
