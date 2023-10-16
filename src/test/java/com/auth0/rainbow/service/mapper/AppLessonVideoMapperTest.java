package com.auth0.rainbow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppLessonVideoMapperTest {

    private AppLessonVideoMapper appLessonVideoMapper;

    @BeforeEach
    public void setUp() {
        appLessonVideoMapper = new AppLessonVideoMapperImpl();
    }
}
