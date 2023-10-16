package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppCartDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppCartDTO.class);
        AppCartDTO appCartDTO1 = new AppCartDTO();
        appCartDTO1.setId(1L);
        AppCartDTO appCartDTO2 = new AppCartDTO();
        assertThat(appCartDTO1).isNotEqualTo(appCartDTO2);
        appCartDTO2.setId(appCartDTO1.getId());
        assertThat(appCartDTO1).isEqualTo(appCartDTO2);
        appCartDTO2.setId(2L);
        assertThat(appCartDTO1).isNotEqualTo(appCartDTO2);
        appCartDTO1.setId(null);
        assertThat(appCartDTO1).isNotEqualTo(appCartDTO2);
    }
}
