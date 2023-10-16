package com.auth0.rainbow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppProductImageMapperTest {

    private AppProductImageMapper appProductImageMapper;

    @BeforeEach
    public void setUp() {
        appProductImageMapper = new AppProductImageMapperImpl();
    }
}
