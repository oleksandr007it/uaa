package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import com.idevhub.tapas.domain.address.NaisAtsObject;
import com.idevhub.tapas.domain.address.NaisAtsObjectStatus;
import com.idevhub.tapas.domain.address.NaisAtsObjectType;
import com.idevhub.tapas.repository.NaisAtsDenormalizedObjectRepository;
import com.idevhub.tapas.repository.NaisAtsObjectRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NaisAtsDictionaryResourceIT {

    @Autowired
    private NaisAtsObjectRepository atsObjectRepository;

    @Autowired
    private NaisAtsDenormalizedObjectRepository denormalizedObjectRepository;

    @Autowired
    private MockMvc mockMvc;

    private static NaisAtsObject stubAtsObject;

    @Test
    @Order(1)
    @WithMockUser
    void denormalizeAtsObjectDictionary() throws Exception {

        // ARRANGE
        stubAtsObject = buildAndSaveStubAtsObject();
        NaisAtsObject stubAtsObjectParent = stubAtsObject.getParent().getParent();

        // ACT
        mockMvc.perform(put("/api/nais/ats/objects/denormalize"))
            .andExpect(status().isOk());

        TimeUnit.SECONDS.sleep(5);

        Optional<NaisAtsDenormalizedObject> expectedOpt = denormalizedObjectRepository.findById(stubAtsObject.getId());
        Optional<NaisAtsDenormalizedObject> expectedParentOpt = denormalizedObjectRepository.findById(stubAtsObjectParent.getId());

        // ASSERT
        assertTrue(expectedOpt.isPresent());
        assertTrue(expectedParentOpt.isPresent());
        assertEquals(expectedOpt.get().getId(), stubAtsObject.getId());
        assertEquals(expectedParentOpt.get().getId(), stubAtsObject.getParent().getParent().getId());
        assertEquals(expectedOpt.get().getLocalityObjectName(), String.format("%s, %s %s", stubAtsObject.getParent().getName(), stubAtsObject.getType().getShortName(), stubAtsObject.getName()));
        assertEquals(expectedParentOpt.get().getLocalityName(), stubAtsObject.getParent().getParent().getName());
    }

    @Test
    @Order(2)
    @WithMockUser
    void searchLocalitiesByName() throws Exception {

        // ARRANGE
        NaisAtsObject expectedObject = stubAtsObject.getParent().getParent();

        // ACT AND ASSERT
        mockMvc.perform(get("/api/nais/ats/localities")
            .param("searchName", "киї"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expectedObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(expectedObject.getName())));
    }

    @Test
    @Order(3)
    @WithMockUser
    void searchLocalityObjectsByName() throws Exception {

        // ARRANGE
        NaisAtsObject localityObject = stubAtsObject.getParent().getParent();

        // ACT AND ASSERT
        mockMvc.perform(get("/api/nais/ats/localities/{}}/objects", localityObject.getId())
            .param("searchName", "земельна"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stubAtsObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(String.format("%s, %s %s", stubAtsObject.getParent().getName(), stubAtsObject.getType().getShortName(), stubAtsObject.getName()))));
    }

    @Test
    @Order(4)
    @WithMockUser
    void getAtsObjectById() throws Exception {

        // ARRANGE
        // ACT AND ASSERT
        mockMvc.perform(get("/api/nais/ats/objects/" + stubAtsObject.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(stubAtsObject.getId().intValue()))
            .andExpect(jsonPath("$.parent.id").value(stubAtsObject.getParent().getParent().getId().intValue()))
            .andExpect(jsonPath("$.localityName").value(stubAtsObject.getParent().getParent().getName()))
            .andExpect(jsonPath("$.localityObjectName").value(String.format("%s, %s %s", stubAtsObject.getParent().getName(), stubAtsObject.getType().getShortName(), stubAtsObject.getName())));
    }

    private NaisAtsObject buildAndSaveStubAtsObject() {

        final NaisAtsObject locality = NaisAtsObject.builder()
            .id(26L)
            .type(NaisAtsObjectType.builder().level(1L).code(1L).build())
            .status(NaisAtsObjectStatus.builder().code(1L).build())
            .koatuuCode("8000000000")
            .name("м.Київ")
            .build();
        atsObjectRepository.saveAndFlush(locality);

        final NaisAtsObject localityObject4Level = NaisAtsObject.builder()
            .id(447530L)
            .parent(locality)
            .type(NaisAtsObjectType.builder().level(4L).code(2L).build())
            .status(NaisAtsObjectStatus.builder().code(1L).build())
            .name("\"Дніпро\" садове товариство (Дарницький р-н)")
            .build();
        atsObjectRepository.saveAndFlush(localityObject4Level);

        final NaisAtsObject localityObject5Level = NaisAtsObject.builder()
            .id(448281L)
            .parent(localityObject4Level)
            .type(NaisAtsObjectType.builder().level(5L).code(1L).build())
            .status(NaisAtsObjectStatus.builder().code(1L).build())
            .name("Малоземельна")
            .build();

        return atsObjectRepository.saveAndFlush(localityObject5Level);
    }
}
