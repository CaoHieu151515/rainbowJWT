package com.auth0.rainbow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppCategoryMapperTest {

    private AppCategoryMapper appCategoryMapper;

    @BeforeEach
    public void setUp() {
        appCategoryMapper = new AppCategoryMapperImpl();
    }
}
