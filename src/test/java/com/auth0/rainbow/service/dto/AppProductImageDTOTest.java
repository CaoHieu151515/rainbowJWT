package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppProductImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppProductImageDTO.class);
        AppProductImageDTO appProductImageDTO1 = new AppProductImageDTO();
        appProductImageDTO1.setId(1L);
        AppProductImageDTO appProductImageDTO2 = new AppProductImageDTO();
        assertThat(appProductImageDTO1).isNotEqualTo(appProductImageDTO2);
        appProductImageDTO2.setId(appProductImageDTO1.getId());
        assertThat(appProductImageDTO1).isEqualTo(appProductImageDTO2);
        appProductImageDTO2.setId(2L);
        assertThat(appProductImageDTO1).isNotEqualTo(appProductImageDTO2);
        appProductImageDTO1.setId(null);
        assertThat(appProductImageDTO1).isNotEqualTo(appProductImageDTO2);
    }
}
