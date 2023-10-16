package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppCategoryDTO.class);
        AppCategoryDTO appCategoryDTO1 = new AppCategoryDTO();
        appCategoryDTO1.setId(1L);
        AppCategoryDTO appCategoryDTO2 = new AppCategoryDTO();
        assertThat(appCategoryDTO1).isNotEqualTo(appCategoryDTO2);
        appCategoryDTO2.setId(appCategoryDTO1.getId());
        assertThat(appCategoryDTO1).isEqualTo(appCategoryDTO2);
        appCategoryDTO2.setId(2L);
        assertThat(appCategoryDTO1).isNotEqualTo(appCategoryDTO2);
        appCategoryDTO1.setId(null);
        assertThat(appCategoryDTO1).isNotEqualTo(appCategoryDTO2);
    }
}
