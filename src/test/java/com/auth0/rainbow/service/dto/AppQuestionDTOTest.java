package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppQuestionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppQuestionDTO.class);
        AppQuestionDTO appQuestionDTO1 = new AppQuestionDTO();
        appQuestionDTO1.setId(1L);
        AppQuestionDTO appQuestionDTO2 = new AppQuestionDTO();
        assertThat(appQuestionDTO1).isNotEqualTo(appQuestionDTO2);
        appQuestionDTO2.setId(appQuestionDTO1.getId());
        assertThat(appQuestionDTO1).isEqualTo(appQuestionDTO2);
        appQuestionDTO2.setId(2L);
        assertThat(appQuestionDTO1).isNotEqualTo(appQuestionDTO2);
        appQuestionDTO1.setId(null);
        assertThat(appQuestionDTO1).isNotEqualTo(appQuestionDTO2);
    }
}
