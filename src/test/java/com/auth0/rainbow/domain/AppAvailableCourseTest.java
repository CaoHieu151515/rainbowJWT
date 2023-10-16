package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppAvailableCourseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppAvailableCourse.class);
        AppAvailableCourse appAvailableCourse1 = new AppAvailableCourse();
        appAvailableCourse1.setId(1L);
        AppAvailableCourse appAvailableCourse2 = new AppAvailableCourse();
        appAvailableCourse2.setId(appAvailableCourse1.getId());
        assertThat(appAvailableCourse1).isEqualTo(appAvailableCourse2);
        appAvailableCourse2.setId(2L);
        assertThat(appAvailableCourse1).isNotEqualTo(appAvailableCourse2);
        appAvailableCourse1.setId(null);
        assertThat(appAvailableCourse1).isNotEqualTo(appAvailableCourse2);
    }
}
