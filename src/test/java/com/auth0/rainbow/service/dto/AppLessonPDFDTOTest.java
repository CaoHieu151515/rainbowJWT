package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLessonPDFDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLessonPDFDTO.class);
        AppLessonPDFDTO appLessonPDFDTO1 = new AppLessonPDFDTO();
        appLessonPDFDTO1.setId(1L);
        AppLessonPDFDTO appLessonPDFDTO2 = new AppLessonPDFDTO();
        assertThat(appLessonPDFDTO1).isNotEqualTo(appLessonPDFDTO2);
        appLessonPDFDTO2.setId(appLessonPDFDTO1.getId());
        assertThat(appLessonPDFDTO1).isEqualTo(appLessonPDFDTO2);
        appLessonPDFDTO2.setId(2L);
        assertThat(appLessonPDFDTO1).isNotEqualTo(appLessonPDFDTO2);
        appLessonPDFDTO1.setId(null);
        assertThat(appLessonPDFDTO1).isNotEqualTo(appLessonPDFDTO2);
    }
}
