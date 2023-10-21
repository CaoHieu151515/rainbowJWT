package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppAvailableCourse;
import com.auth0.rainbow.domain.AppCourse;
import com.auth0.rainbow.domain.AppOrder;
import com.auth0.rainbow.domain.AppOrderItem;
import com.auth0.rainbow.domain.AppPost;
import com.auth0.rainbow.domain.AppUser;
import com.auth0.rainbow.service.dto.AppAvailableCourseDTO;
import com.auth0.rainbow.service.dto.AppCourseDTO;
import com.auth0.rainbow.service.dto.AppOrderDTO;
import com.auth0.rainbow.service.dto.AppOrderItemDTO;
import com.auth0.rainbow.service.dto.AppPostDTO;
import com.auth0.rainbow.service.dto.AppUserDTO;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.checkerframework.checker.units.qual.A;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    AppAvailableCourseMapper other = Mappers.getMapper(AppAvailableCourseMapper.class);

    AppCourseMapper appCoursemap = Mappers.getMapper(AppCourseMapper.class);

    AppPostMapper othMapper = Mappers.getMapper(AppPostMapper.class);

    AppOrderMapper otheOrderMapper = Mappers.getMapper(AppOrderMapper.class);

    @Mapping(target = "courses", source = "courses", qualifiedByName = "appCourseIdSet")
    @Mapping(target = "availableCourses", source = "availableCourses", qualifiedByName = "appAvailableCourseIdSet")
    @Mapping(target = "orders", ignore = true)
    AppUserDTO toDto(AppUser s);

    @Named("toOrderDto")
    @Mappings(
        {
            @Mapping(target = "orders", source = "orders", qualifiedByName = "mapToUserOrderSet"),
            @Mapping(target = "courses", ignore = true),
            @Mapping(target = "availableCourses", ignore = true),
        }
    )
    AppUserDTO toOrderDto(AppUser s);

    @Named("mapToUserOrderSet")
    @BeanMapping(ignoreByDefault = true)
    Set<AppOrderDTO> mapToUserOrderSet(Set<AppOrder> appOrderItem);

    // @Named("toOrderDto")
    // @Mappings({
    //     @Mapping(target = "orders", source = "orders", qualifiedByName = "mapToUserOrderSet"),
    // })
    // AppUserDTO toOrderDto(AppUser s);

    // @Named("mapToUserOrderSet")
    // default Set<AppOrderDTO> mapToUserOrderSet(Set<AppOrder> appOrders) {
    //     if (appOrders == null) {
    //         return Collections.emptySet();
    //     }
    //     return appOrders.stream()
    //             .map(order -> {
    //                 AppOrderDTO orderDTO = AppOrderMapper.INSTANCE.toUserDto(order);
    //                 orderDTO.setUser(null); // Ngăn chặn ánh xạ đệ quy vô hạn
    //                 return orderDTO;
    //             })
    //             .collect(Collectors.toSet());
    // }

    @Mapping(target = "removeCourses", ignore = true)
    @Mapping(target = "removeAvailableCourses", ignore = true)
    AppUser toEntity(AppUserDTO appUserDTO);

    @Named("appCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppCourseDTO toDtoAppCourseId(AppCourse appCourse);

    @Named("appAvailableCourseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppAvailableCourseDTO toDtoAppAvailableCourseId(AppAvailableCourse appAvailableCourse);

    @Named("toUserPostDTO")
    @Mappings({ @Mapping(target = "userposts", source = "posts", qualifiedByName = "mapToUserSet") })
    AppUserDTO toUserPostDTO(AppUser appUser);

    @Named("mapToUserSet")
    static Set<AppPostDTO> mapToUserSet(Set<AppPost> appPost) {
        return appPost
            .stream()
            .map(userposts -> {
                if (userposts == null) {
                    return null;
                }
                userposts.setUser(null);
                return AppPostMapper.INSTANCE.toPOSTDTO(userposts);
            })
            .collect(Collectors.toSet());
    }

    @Named("appCourseIdSet")
    default Set<AppCourseDTO> toDtoAppCourseIdSet(Set<AppCourse> appCourse) {
        if (appCourse == null) {
            return null;
        }
        return appCourse.stream().map(this::toDtoAppCourseId).collect(Collectors.toSet());
    }

    @Named("appAvailableCourseIdSet")
    default Set<AppAvailableCourseDTO> toDtoAppAvailableCourseIdSet(Set<AppAvailableCourse> appAvailableCourse) {
        return appAvailableCourse.stream().map(this::toDtoAppAvailableCourseId).collect(Collectors.toSet());
    }

    @Named("toAppUserDTO")
    @Mappings(
        {
            // Map các trường khác ở đây
            @Mapping(target = "courses", source = "courses", qualifiedByName = "mapToAppCourseSet"),
            @Mapping(target = "availableCourses", source = "availableCourses", qualifiedByName = "mapToAppAvailableCourseSet"),
        }
    )
    AppUserDTO toAppUserDTO(AppUser appUser);

    @Named("mapToAppCourseSet")
    static Set<AppCourseDTO> mapToAppCourseSet(Set<AppCourse> appCourses) {
        return appCourses
            .stream()
            .map(appCourse -> {
                if (appCourse == null) {
                    return null;
                }
                return AppCourseMapper.INSTANCE.toDto(appCourse);
            })
            .collect(Collectors.toSet());
    }

    @Named("mapToAppAvailableCourseSet")
    static Set<AppAvailableCourseDTO> mapToAppAvailableCourseSet(Set<AppAvailableCourse> appAvailableCourses) {
        return appAvailableCourses
            .stream()
            .map(appAvailableCourse -> {
                if (appAvailableCourse == null) {
                    return null;
                }
                return AppAvailableCourseMapper.INSTANCE.toAvaiCourseDTO(appAvailableCourse);
            })
            .collect(Collectors.toSet());
    }
}
