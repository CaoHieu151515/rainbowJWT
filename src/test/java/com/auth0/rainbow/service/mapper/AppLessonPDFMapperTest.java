package com.auth0.rainbow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppLessonPDFMapperTest {

    private AppLessonPDFMapper appLessonPDFMapper;

    @BeforeEach
    public void setUp() {
        appLessonPDFMapper = new AppLessonPDFMapperImpl();
    }
}
