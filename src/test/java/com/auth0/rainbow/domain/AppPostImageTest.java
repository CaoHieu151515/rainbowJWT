package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppPostImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppPostImage.class);
        AppPostImage appPostImage1 = new AppPostImage();
        appPostImage1.setId(1L);
        AppPostImage appPostImage2 = new AppPostImage();
        appPostImage2.setId(appPostImage1.getId());
        assertThat(appPostImage1).isEqualTo(appPostImage2);
        appPostImage2.setId(2L);
        assertThat(appPostImage1).isNotEqualTo(appPostImage2);
        appPostImage1.setId(null);
        assertThat(appPostImage1).isNotEqualTo(appPostImage2);
    }
}
