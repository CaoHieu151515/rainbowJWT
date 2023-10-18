package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppQuestionVideoInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppQuestionVideoInfoDTO.class);
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO1 = new AppQuestionVideoInfoDTO();
        appQuestionVideoInfoDTO1.setId(1L);
        AppQuestionVideoInfoDTO appQuestionVideoInfoDTO2 = new AppQuestionVideoInfoDTO();
        assertThat(appQuestionVideoInfoDTO1).isNotEqualTo(appQuestionVideoInfoDTO2);
        appQuestionVideoInfoDTO2.setId(appQuestionVideoInfoDTO1.getId());
        assertThat(appQuestionVideoInfoDTO1).isEqualTo(appQuestionVideoInfoDTO2);
        appQuestionVideoInfoDTO2.setId(2L);
        assertThat(appQuestionVideoInfoDTO1).isNotEqualTo(appQuestionVideoInfoDTO2);
        appQuestionVideoInfoDTO1.setId(null);
        assertThat(appQuestionVideoInfoDTO1).isNotEqualTo(appQuestionVideoInfoDTO2);
    }
}
