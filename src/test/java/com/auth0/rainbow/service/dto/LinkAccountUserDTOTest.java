package com.auth0.rainbow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.rainbow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinkAccountUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkAccountUserDTO.class);
        LinkAccountUserDTO linkAccountUserDTO1 = new LinkAccountUserDTO();
        linkAccountUserDTO1.setId(1L);
        LinkAccountUserDTO linkAccountUserDTO2 = new LinkAccountUserDTO();
        assertThat(linkAccountUserDTO1).isNotEqualTo(linkAccountUserDTO2);
        linkAccountUserDTO2.setId(linkAccountUserDTO1.getId());
        assertThat(linkAccountUserDTO1).isEqualTo(linkAccountUserDTO2);
        linkAccountUserDTO2.setId(2L);
        assertThat(linkAccountUserDTO1).isNotEqualTo(linkAccountUserDTO2);
        linkAccountUserDTO1.setId(null);
        assertThat(linkAccountUserDTO1).isNotEqualTo(linkAccountUserDTO2);
    }
}
