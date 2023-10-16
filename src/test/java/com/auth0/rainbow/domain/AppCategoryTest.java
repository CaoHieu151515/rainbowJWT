package com.auth0.rainbow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppCategory.class);
        AppCategory appCategory1 = new AppCategory();
        appCategory1.setId(1L);
        AppCategory appCategory2 = new AppCategory();
        appCategory2.setId(appCategory1.getId());
        assertThat(appCategory1).isEqualTo(appCategory2);
        appCategory2.setId(2L);
        assertThat(appCategory1).isNotEqualTo(appCategory2);
        appCategory1.setId(null);
        assertThat(appCategory1).isNotEqualTo(appCategory2);
    }
}
