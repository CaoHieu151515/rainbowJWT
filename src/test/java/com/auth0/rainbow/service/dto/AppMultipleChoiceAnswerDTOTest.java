package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppMultipleChoiceAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppMultipleChoiceAnswerDTO.class);
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO1 = new AppMultipleChoiceAnswerDTO();
        appMultipleChoiceAnswerDTO1.setId(1L);
        AppMultipleChoiceAnswerDTO appMultipleChoiceAnswerDTO2 = new AppMultipleChoiceAnswerDTO();
        assertThat(appMultipleChoiceAnswerDTO1).isNotEqualTo(appMultipleChoiceAnswerDTO2);
        appMultipleChoiceAnswerDTO2.setId(appMultipleChoiceAnswerDTO1.getId());
        assertThat(appMultipleChoiceAnswerDTO1).isEqualTo(appMultipleChoiceAnswerDTO2);
        appMultipleChoiceAnswerDTO2.setId(2L);
        assertThat(appMultipleChoiceAnswerDTO1).isNotEqualTo(appMultipleChoiceAnswerDTO2);
        appMultipleChoiceAnswerDTO1.setId(null);
        assertThat(appMultipleChoiceAnswerDTO1).isNotEqualTo(appMultipleChoiceAnswerDTO2);
    }
}
