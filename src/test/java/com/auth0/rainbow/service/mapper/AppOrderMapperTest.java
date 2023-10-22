package com.auth0.rainbow.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class AppOrderMapperTest {

    private AppOrderMapper appOrderMapper;

    @BeforeEach
    public void setUp() {
        appOrderMapper = new AppOrderMapperImpl();
    }
}
