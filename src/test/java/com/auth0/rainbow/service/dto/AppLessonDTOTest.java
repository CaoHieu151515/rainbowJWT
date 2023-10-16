package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLessonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLessonDTO.class);
        AppLessonDTO appLessonDTO1 = new AppLessonDTO();
        appLessonDTO1.setId(1L);
        AppLessonDTO appLessonDTO2 = new AppLessonDTO();
        assertThat(appLessonDTO1).isNotEqualTo(appLessonDTO2);
        appLessonDTO2.setId(appLessonDTO1.getId());
        assertThat(appLessonDTO1).isEqualTo(appLessonDTO2);
        appLessonDTO2.setId(2L);
        assertThat(appLessonDTO1).isNotEqualTo(appLessonDTO2);
        appLessonDTO1.setId(null);
        assertThat(appLessonDTO1).isNotEqualTo(appLessonDTO2);
    }
}
