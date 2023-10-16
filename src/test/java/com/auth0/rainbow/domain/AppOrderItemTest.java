package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppOrderItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppOrderItem.class);
        AppOrderItem appOrderItem1 = new AppOrderItem();
        appOrderItem1.setId(1L);
        AppOrderItem appOrderItem2 = new AppOrderItem();
        appOrderItem2.setId(appOrderItem1.getId());
        assertThat(appOrderItem1).isEqualTo(appOrderItem2);
        appOrderItem2.setId(2L);
        assertThat(appOrderItem1).isNotEqualTo(appOrderItem2);
        appOrderItem1.setId(null);
        assertThat(appOrderItem1).isNotEqualTo(appOrderItem2);
    }
}
