package com.auth0.rainbow.service.mapper;

import com.auth0.rainbow.domain.AppCategory;
import com.auth0.rainbow.domain.AppProduct;
import com.auth0.rainbow.domain.AppProductImage;
import com.auth0.rainbow.service.dto.AppCategoryDTO;
import com.auth0.rainbow.service.dto.AppProductDTO;
import com.auth0.rainbow.service.dto.AppProductImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppProduct} and its DTO {@link AppProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppProductMapper extends EntityMapper<AppProductDTO, AppProduct> {
    @Mapping(target = "category", source = "category", qualifiedByName = "appCategoryId")
    @Mapping(target = "images", source = "images", qualifiedByName = "appProductImageId")
    AppProductDTO toDto(AppProduct s);

    @Named("appCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppCategoryDTO toDtoAppCategoryId(AppCategory appCategory);

    @Named("appProductImageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppProductImageDTO toDtoAppProductImageId(AppProductImage appProductImage);
}
