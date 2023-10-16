package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppAvailableCourseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppAvailableCourseDTO.class);
        AppAvailableCourseDTO appAvailableCourseDTO1 = new AppAvailableCourseDTO();
        appAvailableCourseDTO1.setId(1L);
        AppAvailableCourseDTO appAvailableCourseDTO2 = new AppAvailableCourseDTO();
        assertThat(appAvailableCourseDTO1).isNotEqualTo(appAvailableCourseDTO2);
        appAvailableCourseDTO2.setId(appAvailableCourseDTO1.getId());
        assertThat(appAvailableCourseDTO1).isEqualTo(appAvailableCourseDTO2);
        appAvailableCourseDTO2.setId(2L);
        assertThat(appAvailableCourseDTO1).isNotEqualTo(appAvailableCourseDTO2);
        appAvailableCourseDTO1.setId(null);
        assertThat(appAvailableCourseDTO1).isNotEqualTo(appAvailableCourseDTO2);
    }
}
