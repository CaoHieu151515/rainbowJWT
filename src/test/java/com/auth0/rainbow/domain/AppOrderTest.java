package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppOrder.class);
        AppOrder appOrder1 = new AppOrder();
        appOrder1.setId(1L);
        AppOrder appOrder2 = new AppOrder();
        appOrder2.setId(appOrder1.getId());
        assertThat(appOrder1).isEqualTo(appOrder2);
        appOrder2.setId(2L);
        assertThat(appOrder1).isNotEqualTo(appOrder2);
        appOrder1.setId(null);
        assertThat(appOrder1).isNotEqualTo(appOrder2);
    }
}
