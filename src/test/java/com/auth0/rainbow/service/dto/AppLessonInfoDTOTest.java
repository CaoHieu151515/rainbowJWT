package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLessonInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLessonInfoDTO.class);
        AppLessonInfoDTO appLessonInfoDTO1 = new AppLessonInfoDTO();
        appLessonInfoDTO1.setId(1L);
        AppLessonInfoDTO appLessonInfoDTO2 = new AppLessonInfoDTO();
        assertThat(appLessonInfoDTO1).isNotEqualTo(appLessonInfoDTO2);
        appLessonInfoDTO2.setId(appLessonInfoDTO1.getId());
        assertThat(appLessonInfoDTO1).isEqualTo(appLessonInfoDTO2);
        appLessonInfoDTO2.setId(2L);
        assertThat(appLessonInfoDTO1).isNotEqualTo(appLessonInfoDTO2);
        appLessonInfoDTO1.setId(null);
        assertThat(appLessonInfoDTO1).isNotEqualTo(appLessonInfoDTO2);
    }
}
