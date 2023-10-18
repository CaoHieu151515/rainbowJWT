package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLessonPDFTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLessonPDF.class);
        AppLessonPDF appLessonPDF1 = new AppLessonPDF();
        appLessonPDF1.setId(1L);
        AppLessonPDF appLessonPDF2 = new AppLessonPDF();
        appLessonPDF2.setId(appLessonPDF1.getId());
        assertThat(appLessonPDF1).isEqualTo(appLessonPDF2);
        appLessonPDF2.setId(2L);
        assertThat(appLessonPDF1).isNotEqualTo(appLessonPDF2);
        appLessonPDF1.setId(null);
        assertThat(appLessonPDF1).isNotEqualTo(appLessonPDF2);
    }
}
