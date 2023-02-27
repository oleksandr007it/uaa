package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.repository.KopfgRepository;
import com.idevhub.tapas.service.dto.KopfgDTO;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KopfgServiceImplTest {
    @Autowired
    private KopfgRepository kopfgRepository;

    @Autowired
    private KopfgServiceImpl kopfgService;

    @Test
    public void testSave() {
        final KopfgDTO actualKopfgDTO = getStubKopfgDTO();
        final KopfgDTO expectedKopfgDTO = kopfgService.save(actualKopfgDTO);

        assertThat(expectedKopfgDTO).isNotNull();
        assertThat(expectedKopfgDTO.getName()).isNotEmpty();
        assertThat(expectedKopfgDTO.getPreviousCodes()).isNotEmpty();
    }

    @Test
    public void testFindAll() {
        final Kopfg kopfg = getStubKopfg();
        kopfgRepository.saveAndFlush(kopfg);

        final List<KopfgDTO> result = kopfgService.findAll();

        assertThat(result).hasSizeGreaterThanOrEqualTo(1);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getName()).isNotEmpty();
        assertThat(result.get(0).getPreviousCodes()).isNotEmpty();
    }

    @Test
    @Disabled  // TODO: fix this test
    public void testFindOne() {
        final Kopfg kopfg = getStubKopfg();
        kopfgRepository.saveAndFlush(kopfg);

        final List<KopfgDTO> listDomains = kopfgService.findAll();
        final long id = listDomains.get(listDomains.size() - 1).getId();

        final Optional<KopfgDTO> result = kopfgService.findOne(id);

        result.ifPresent(v -> assertThat(v).isNotNull());
        result.ifPresent(v -> assertThat(v.getName()).isNotEmpty());
        result.ifPresent(v -> assertThat(v.getPreviousCodes()).isNotEmpty());
    }

    @Test
    public void testDelete() {
        final Kopfg kopfg = getStubKopfg();
        kopfgRepository.saveAndFlush(kopfg);

        final List<Kopfg> listDomains = kopfgRepository.findAll();
        final int beforeSize = listDomains.size();
        final long id = listDomains.get(beforeSize - 1).getId();

        kopfgService.delete(id);

        final int afterSize = kopfgRepository.findAll().size();

        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }

    private KopfgDTO getStubKopfgDTO() {
        final KopfgDTO kopfg = new KopfgDTO();
        kopfg.setId(1L);
        kopfg.setCode(3);
        kopfg.setName("kopfg_name");
        kopfg.setPreviousCodes("321");
        kopfg.setValidUntil(Instant.now());

        return kopfg;
    }

    private Kopfg getStubKopfg() {
        final Kopfg kopfg = new Kopfg();
        kopfg.setId(1L);
        kopfg.setCode(3);
        kopfg.setName("kopfg_name");
        kopfg.setPreviousCodes("321");
        kopfg.setValidUntil(Instant.now());

        return kopfg;
    }
}
