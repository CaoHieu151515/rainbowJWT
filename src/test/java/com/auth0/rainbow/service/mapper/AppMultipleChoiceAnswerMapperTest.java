package com.auth0.rainbow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppMultipleChoiceAnswerMapperTest {

    private AppMultipleChoiceAnswerMapper appMultipleChoiceAnswerMapper;

    @BeforeEach
    public void setUp() {
        appMultipleChoiceAnswerMapper = new AppMultipleChoiceAnswerMapperImpl();
    }
}
