package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppOrderItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppOrderItemDTO.class);
        AppOrderItemDTO appOrderItemDTO1 = new AppOrderItemDTO();
        appOrderItemDTO1.setId(1L);
        AppOrderItemDTO appOrderItemDTO2 = new AppOrderItemDTO();
        assertThat(appOrderItemDTO1).isNotEqualTo(appOrderItemDTO2);
        appOrderItemDTO2.setId(appOrderItemDTO1.getId());
        assertThat(appOrderItemDTO1).isEqualTo(appOrderItemDTO2);
        appOrderItemDTO2.setId(2L);
        assertThat(appOrderItemDTO1).isNotEqualTo(appOrderItemDTO2);
        appOrderItemDTO1.setId(null);
        assertThat(appOrderItemDTO1).isNotEqualTo(appOrderItemDTO2);
    }
}
