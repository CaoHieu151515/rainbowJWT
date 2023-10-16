package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCategory;
import com.auth0.rainbow.service.dto.AppCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppCategory} and its DTO {@link AppCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppCategoryMapper extends EntityMapper<AppCategoryDTO, AppCategory> {}
