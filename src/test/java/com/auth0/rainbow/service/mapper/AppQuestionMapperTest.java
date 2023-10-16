package com.auth0.rainbow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppQuestionMapperTest {

    private AppQuestionMapper appQuestionMapper;

    @BeforeEach
    public void setUp() {
        appQuestionMapper = new AppQuestionMapperImpl();
    }
}
