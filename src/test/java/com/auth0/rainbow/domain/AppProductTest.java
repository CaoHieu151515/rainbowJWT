package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppProduct.class);
        AppProduct appProduct1 = new AppProduct();
        appProduct1.setId(1L);
        AppProduct appProduct2 = new AppProduct();
        appProduct2.setId(appProduct1.getId());
        assertThat(appProduct1).isEqualTo(appProduct2);
        appProduct2.setId(2L);
        assertThat(appProduct1).isNotEqualTo(appProduct2);
        appProduct1.setId(null);
        assertThat(appProduct1).isNotEqualTo(appProduct2);
    }
}
