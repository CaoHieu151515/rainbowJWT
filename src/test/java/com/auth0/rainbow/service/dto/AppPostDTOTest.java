package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppPostDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppPostDTO.class);
        AppPostDTO appPostDTO1 = new AppPostDTO();
        appPostDTO1.setId(1L);
        AppPostDTO appPostDTO2 = new AppPostDTO();
        assertThat(appPostDTO1).isNotEqualTo(appPostDTO2);
        appPostDTO2.setId(appPostDTO1.getId());
        assertThat(appPostDTO1).isEqualTo(appPostDTO2);
        appPostDTO2.setId(2L);
        assertThat(appPostDTO1).isNotEqualTo(appPostDTO2);
        appPostDTO1.setId(null);
        assertThat(appPostDTO1).isNotEqualTo(appPostDTO2);
    }
}
