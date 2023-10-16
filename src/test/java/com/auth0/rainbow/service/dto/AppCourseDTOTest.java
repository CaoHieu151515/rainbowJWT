package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppCourseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppCourseDTO.class);
        AppCourseDTO appCourseDTO1 = new AppCourseDTO();
        appCourseDTO1.setId(1L);
        AppCourseDTO appCourseDTO2 = new AppCourseDTO();
        assertThat(appCourseDTO1).isNotEqualTo(appCourseDTO2);
        appCourseDTO2.setId(appCourseDTO1.getId());
        assertThat(appCourseDTO1).isEqualTo(appCourseDTO2);
        appCourseDTO2.setId(2L);
        assertThat(appCourseDTO1).isNotEqualTo(appCourseDTO2);
        appCourseDTO1.setId(null);
        assertThat(appCourseDTO1).isNotEqualTo(appCourseDTO2);
    }
}
