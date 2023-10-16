package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppPaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppPayment.class);
        AppPayment appPayment1 = new AppPayment();
        appPayment1.setId(1L);
        AppPayment appPayment2 = new AppPayment();
        appPayment2.setId(appPayment1.getId());
        assertThat(appPayment1).isEqualTo(appPayment2);
        appPayment2.setId(2L);
        assertThat(appPayment1).isNotEqualTo(appPayment2);
        appPayment1.setId(null);
        assertThat(appPayment1).isNotEqualTo(appPayment2);
    }
}
