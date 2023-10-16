package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppOrderDTO.class);
        AppOrderDTO appOrderDTO1 = new AppOrderDTO();
        appOrderDTO1.setId(1L);
        AppOrderDTO appOrderDTO2 = new AppOrderDTO();
        assertThat(appOrderDTO1).isNotEqualTo(appOrderDTO2);
        appOrderDTO2.setId(appOrderDTO1.getId());
        assertThat(appOrderDTO1).isEqualTo(appOrderDTO2);
        appOrderDTO2.setId(2L);
        assertThat(appOrderDTO1).isNotEqualTo(appOrderDTO2);
        appOrderDTO1.setId(null);
        assertThat(appOrderDTO1).isNotEqualTo(appOrderDTO2);
    }
}
