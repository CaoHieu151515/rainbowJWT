package com.auth0.rainbow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppQuestionVideoInfoMapperTest {

    private AppQuestionVideoInfoMapper appQuestionVideoInfoMapper;

    @BeforeEach
    public void setUp() {
        appQuestionVideoInfoMapper = new AppQuestionVideoInfoMapperImpl();
    }
}
