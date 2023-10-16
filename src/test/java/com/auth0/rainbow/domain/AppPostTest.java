package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppPostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppPost.class);
        AppPost appPost1 = new AppPost();
        appPost1.setId(1L);
        AppPost appPost2 = new AppPost();
        appPost2.setId(appPost1.getId());
        assertThat(appPost1).isEqualTo(appPost2);
        appPost2.setId(2L);
        assertThat(appPost1).isNotEqualTo(appPost2);
        appPost1.setId(null);
        assertThat(appPost1).isNotEqualTo(appPost2);
    }
}
