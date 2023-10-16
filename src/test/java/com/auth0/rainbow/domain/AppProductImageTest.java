package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppProductImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppProductImage.class);
        AppProductImage appProductImage1 = new AppProductImage();
        appProductImage1.setId(1L);
        AppProductImage appProductImage2 = new AppProductImage();
        appProductImage2.setId(appProductImage1.getId());
        assertThat(appProductImage1).isEqualTo(appProductImage2);
        appProductImage2.setId(2L);
        assertThat(appProductImage1).isNotEqualTo(appProductImage2);
        appProductImage1.setId(null);
        assertThat(appProductImage1).isNotEqualTo(appProductImage2);
    }
}
