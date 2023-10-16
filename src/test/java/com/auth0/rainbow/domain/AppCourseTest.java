package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppCourseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppCourse.class);
        AppCourse appCourse1 = new AppCourse();
        appCourse1.setId(1L);
        AppCourse appCourse2 = new AppCourse();
        appCourse2.setId(appCourse1.getId());
        assertThat(appCourse1).isEqualTo(appCourse2);
        appCourse2.setId(2L);
        assertThat(appCourse1).isNotEqualTo(appCourse2);
        appCourse1.setId(null);
        assertThat(appCourse1).isNotEqualTo(appCourse2);
    }
}
