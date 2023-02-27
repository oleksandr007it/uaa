package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.service.AddressService;
import com.idevhub.tapas.service.dto.AddressDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class    AddressResource {

    private static final String ENTITY_NAME = "address";
    private final Logger log = LoggerFactory.getLogger(AddressResource.class);
    private final AddressService addressService;
    @Value("${jhipster.clientApp.name}")
    private String APPLICATION_NAME;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * GET  /addresses : get all the addresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addresses in body
     */
    @GetMapping("/addresses")
    @Timed
    public List<AddressDTO> getAllAddresses() {
        log.debug("REST request to get all Addresses");
        return addressService.findAll();
    }

    /**
     * GET  /addresses/:id : get the "id" address.
     *
     * @param id the id of the addressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<AddressDTO> getAddress(@PathVariable String id) {
        log.debug("REST request to get Address : {}", id);
        Optional<AddressDTO> addressDTO = addressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressDTO);
    }
}
