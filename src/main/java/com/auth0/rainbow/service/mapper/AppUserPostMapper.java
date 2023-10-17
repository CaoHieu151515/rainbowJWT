package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import com.auth0.rainbow.service.dto.AppUserPostDTO;
import com.google.inject.name.Named;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppUserPostMapper extends EntityMapper<AppUserPostDTO, AppUser> {
    AppUserPostMapper INSTANCE = Mappers.getMapper(AppUserPostMapper.class);
    AppPostMapper othMapper = Mappers.getMapper(AppPostMapper.class);
    // @Named("toUserPostDTO")
    // @Mappings(
    //     {
    //         // Map các trường khác ở đây
    //         @Mapping(target = "postShare", source = "post", qualifiedByName = "mapToUserPostSet"),

    //     }
    // )AppUserPostDTO toUserPostDTO(AppPost AppPost);

    // @Named("mapToUserPostSet")
    // static Set<AppUserPostDTO> mapToUserPostSet(Set<AppPost> appPost) {
    //     return appPost
    //         .stream()
    //         .map(postShare -> {
    //             if (postShare == null) {
    //                 return null;
    //             }
    //             return AppPostMapper.INSTANCE.toPOSTDTO(postShare);
    //         })
    //         .collect(Collectors.toSet());
    // }

}
