package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.service.KopfgService;
import com.idevhub.tapas.service.dto.KopfgDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class KopfgResourceTest {

    @Mock
    private KopfgService mockKopfgService;

    private KopfgResource kopfgResourceUnderTest;

    private KopfgDTO kopfgDTO = getKopfgDTO();

    @BeforeEach
    void setUp() {
        initMocks(this);
        kopfgResourceUnderTest = new KopfgResource(mockKopfgService);
    }

    private KopfgDTO getKopfgDTO() {
        KopfgDTO kopfgDTO = new KopfgDTO();

        kopfgDTO.setId(0L);
        kopfgDTO.setCode(0);
        kopfgDTO.setName("name");
        kopfgDTO.setValidUntil(Instant.ofEpochSecond(0L));
        kopfgDTO.setPreviousCodes("previousCodes");

        return kopfgDTO;
    }

    @Test
    void testCreateKopfg() throws Exception {
        // Setup
        KopfgDTO input = new KopfgDTO();
        input.setCode(0);
        input.setName("name");
        input.setValidUntil(Instant.ofEpochSecond(0L));
        input.setPreviousCodes("previousCodes");

        when(mockKopfgService.save(any(KopfgDTO.class))).thenReturn(kopfgDTO);

        // Run the test
        final KopfgDTO result = kopfgResourceUnderTest.createKopfg(input).getBody();

        input.setId(kopfgDTO.getId());

        // Verify the results
        assertEquals(input, result);
    }

    @Test
    void testUpdateKopfg() throws Exception {
        // Setup
        final KopfgDTO expectedResult = kopfgDTO;

        when(mockKopfgService.save(any(KopfgDTO.class))).thenReturn(kopfgDTO);

        // Run the test
        final KopfgDTO result = kopfgResourceUnderTest.updateKopfg(kopfgDTO).getBody();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllKopfgS() {
        // Setup
        final List<KopfgDTO> expectedResult = List.of(kopfgDTO);

        when(mockKopfgService.findAll()).thenReturn(expectedResult);

        // Run the test
        final List<KopfgDTO> result = kopfgResourceUnderTest.getAllKopfgS();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetKopfg() {
        // Setup
        final KopfgDTO expectedResult = kopfgDTO;

        when(mockKopfgService.findOne(anyLong())).thenReturn(Optional.of(kopfgDTO));

        // Run the test
        final KopfgDTO result = kopfgResourceUnderTest.getKopfg(0L).getBody();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeleteKopfg() {
        // Setup

        // Run the test
        final ResponseEntity<Void> result = kopfgResourceUnderTest.deleteKopfg(0L);

        // Verify the results
        verify(mockKopfgService).delete(0L);
    }
}
