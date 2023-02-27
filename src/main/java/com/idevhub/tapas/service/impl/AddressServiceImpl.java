package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.repository.AddressRepository;
import com.idevhub.tapas.service.AddressService;
import com.idevhub.tapas.service.dto.AddressDTO;
import com.idevhub.tapas.service.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Address.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    /**
     * Get all the addresses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressDTO> findAll() {
        log.debug("Request to get all Addresses");

        List<Address> targetList = addressRepository.findAll();

        return targetList.stream()
            .map(addressMapper::toDto)
            .collect(Collectors.toList());
    }


    /**
     * Get one address by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDTO> findOne(String id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id)
            .map(addressMapper::toDto);
    }

}
