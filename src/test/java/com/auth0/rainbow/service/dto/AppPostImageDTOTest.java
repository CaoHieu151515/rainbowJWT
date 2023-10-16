package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppPostImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppPostImageDTO.class);
        AppPostImageDTO appPostImageDTO1 = new AppPostImageDTO();
        appPostImageDTO1.setId(1L);
        AppPostImageDTO appPostImageDTO2 = new AppPostImageDTO();
        assertThat(appPostImageDTO1).isNotEqualTo(appPostImageDTO2);
        appPostImageDTO2.setId(appPostImageDTO1.getId());
        assertThat(appPostImageDTO1).isEqualTo(appPostImageDTO2);
        appPostImageDTO2.setId(2L);
        assertThat(appPostImageDTO1).isNotEqualTo(appPostImageDTO2);
        appPostImageDTO1.setId(null);
        assertThat(appPostImageDTO1).isNotEqualTo(appPostImageDTO2);
    }
}
