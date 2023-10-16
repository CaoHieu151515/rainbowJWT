package com.auth0.rainbow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppOrderItemMapperTest {

    private AppOrderItemMapper appOrderItemMapper;

    @BeforeEach
    public void setUp() {
        appOrderItemMapper = new AppOrderItemMapperImpl();
    }
}
