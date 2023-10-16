package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppProductDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppProductDTO.class);
        AppProductDTO appProductDTO1 = new AppProductDTO();
        appProductDTO1.setId(1L);
        AppProductDTO appProductDTO2 = new AppProductDTO();
        assertThat(appProductDTO1).isNotEqualTo(appProductDTO2);
        appProductDTO2.setId(appProductDTO1.getId());
        assertThat(appProductDTO1).isEqualTo(appProductDTO2);
        appProductDTO2.setId(2L);
        assertThat(appProductDTO1).isNotEqualTo(appProductDTO2);
        appProductDTO1.setId(null);
        assertThat(appProductDTO1).isNotEqualTo(appProductDTO2);
    }
}
