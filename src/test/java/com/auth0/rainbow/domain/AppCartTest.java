package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppCartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppCart.class);
        AppCart appCart1 = new AppCart();
        appCart1.setId(1L);
        AppCart appCart2 = new AppCart();
        appCart2.setId(appCart1.getId());
        assertThat(appCart1).isEqualTo(appCart2);
        appCart2.setId(2L);
        assertThat(appCart1).isNotEqualTo(appCart2);
        appCart1.setId(null);
        assertThat(appCart1).isNotEqualTo(appCart2);
    }
}
