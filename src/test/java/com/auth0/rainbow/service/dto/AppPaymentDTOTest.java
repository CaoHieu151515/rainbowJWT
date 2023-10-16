package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppPaymentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppPaymentDTO.class);
        AppPaymentDTO appPaymentDTO1 = new AppPaymentDTO();
        appPaymentDTO1.setId(1L);
        AppPaymentDTO appPaymentDTO2 = new AppPaymentDTO();
        assertThat(appPaymentDTO1).isNotEqualTo(appPaymentDTO2);
        appPaymentDTO2.setId(appPaymentDTO1.getId());
        assertThat(appPaymentDTO1).isEqualTo(appPaymentDTO2);
        appPaymentDTO2.setId(2L);
        assertThat(appPaymentDTO1).isNotEqualTo(appPaymentDTO2);
        appPaymentDTO1.setId(null);
        assertThat(appPaymentDTO1).isNotEqualTo(appPaymentDTO2);
    }
}
