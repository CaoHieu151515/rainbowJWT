package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLessonVideoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLessonVideoDTO.class);
        AppLessonVideoDTO appLessonVideoDTO1 = new AppLessonVideoDTO();
        appLessonVideoDTO1.setId(1L);
        AppLessonVideoDTO appLessonVideoDTO2 = new AppLessonVideoDTO();
        assertThat(appLessonVideoDTO1).isNotEqualTo(appLessonVideoDTO2);
        appLessonVideoDTO2.setId(appLessonVideoDTO1.getId());
        assertThat(appLessonVideoDTO1).isEqualTo(appLessonVideoDTO2);
        appLessonVideoDTO2.setId(2L);
        assertThat(appLessonVideoDTO1).isNotEqualTo(appLessonVideoDTO2);
        appLessonVideoDTO1.setId(null);
        assertThat(appLessonVideoDTO1).isNotEqualTo(appLessonVideoDTO2);
    }
}
