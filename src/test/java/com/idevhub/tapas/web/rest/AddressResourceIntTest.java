package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.repository.AddressRepository;
import com.idevhub.tapas.service.AddressService;
import com.idevhub.tapas.service.dto.AddressDTO;
import com.idevhub.tapas.service.mapper.AddressMapper;
import com.idevhub.tapas.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.idevhub.tapas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AddressResource REST controller.
 *
 * @see AddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
public class AddressResourceIntTest {

    private static final String DEFAULT_COUNTRY_CODE = "804";

    private static final String DEFAULT_INDEX = "AAAAA";
    private static final String UPDATED_INDEX = "BBBBB";

    private static final Long DEFAULT_ADMIN_TERRITORIAL_OBJECT = 1L;
    private static final Long UPDATED_ADMIN_TERRITORIAL_OBJECT = 2L;

    private static final String DEFAULT_BUILDING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK = "BBBBBBBBBB";

    private static final String DEFAULT_APPARTMENT_NUMBER = "1";
    private static final String UPDATED_APPARTMENT_NUMBER = "2";

    private static final String DEFAULT_FULL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FULL_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private AddressRepository addressRepository;


    @Autowired
    private AddressMapper addressMapper;


    @Autowired
    private AddressService addressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddressMockMvc;

    private Address address;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressResource addressResource = new AddressResource(addressService);
        this.restAddressMockMvc = MockMvcBuilders.standaloneSetup(addressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address();
        address.setCountryCode(DEFAULT_COUNTRY_CODE);
        address.setPostalCode(DEFAULT_INDEX);
        address.setAtsObjectId(DEFAULT_ADMIN_TERRITORIAL_OBJECT);
        address.setHouseNumber(DEFAULT_BUILDING_NUMBER);
        address.setPavilionNumber(DEFAULT_BLOCK);
        address.setApartmentNumber(DEFAULT_APPARTMENT_NUMBER);
        return address;
    }

    @Before
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getPostalCode()).isEqualTo(DEFAULT_INDEX);
        assertThat(testAddress.getAtsObjectId()).isEqualTo(DEFAULT_ADMIN_TERRITORIAL_OBJECT);
        assertThat(testAddress.getHouseNumber()).isEqualTo(DEFAULT_BUILDING_NUMBER);
        assertThat(testAddress.getPavilionNumber()).isEqualTo(DEFAULT_BLOCK);
        assertThat(testAddress.getApartmentNumber()).isEqualTo(DEFAULT_APPARTMENT_NUMBER);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId("550e8400e29b41d4a716446655440000");
        AddressDTO addressDTO = addressMapper.toDto(address);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setCountryCode(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuildingNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setHouseNumber(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].atsObjectId").value(hasItem(DEFAULT_ADMIN_TERRITORIAL_OBJECT.intValue())))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_BUILDING_NUMBER)))
            .andExpect(jsonPath("$.[*].pavilionNumber").value(hasItem(DEFAULT_BLOCK)))
            .andExpect(jsonPath("$.[*].apartmentNumber").value(hasItem(DEFAULT_APPARTMENT_NUMBER)));
    }


    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.atsObjectId").value(DEFAULT_ADMIN_TERRITORIAL_OBJECT.intValue()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_BUILDING_NUMBER))
            .andExpect(jsonPath("$.pavilionNumber").value(DEFAULT_BLOCK))
            .andExpect(jsonPath("$.apartmentNumber").value(DEFAULT_APPARTMENT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress.setPostalCode(UPDATED_INDEX);
        updatedAddress.setAtsObjectId(UPDATED_ADMIN_TERRITORIAL_OBJECT);
        updatedAddress.setHouseNumber(UPDATED_BUILDING_NUMBER);
        updatedAddress.setPavilionNumber(UPDATED_BLOCK);
        updatedAddress.setApartmentNumber(UPDATED_APPARTMENT_NUMBER);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getPostalCode()).isEqualTo(UPDATED_INDEX);
        assertThat(testAddress.getAtsObjectId()).isEqualTo(UPDATED_ADMIN_TERRITORIAL_OBJECT);
        assertThat(testAddress.getHouseNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testAddress.getPavilionNumber()).isEqualTo(UPDATED_BLOCK);
        assertThat(testAddress.getApartmentNumber()).isEqualTo(UPDATED_APPARTMENT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Get the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = new Address();
        address1.setId("550e8400e29b41d4a716446655440000");
        Address address2 = new Address();
        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);
        address2.setId("550e8400e29b41d4a716446655441111");
        assertThat(address1).isNotEqualTo(address2);
        address1.setId(null);
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressDTO.class);
        AddressDTO addressDTO1 = new AddressDTO();
        addressDTO1.setId("550e8400e29b41d4a716446655440000");
        AddressDTO addressDTO2 = new AddressDTO();
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
        addressDTO2.setId(addressDTO1.getId());
        assertThat(addressDTO1).isEqualTo(addressDTO2);
        addressDTO2.setId("550e8400e29b41d4a716446655441111");
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
        addressDTO1.setId(null);
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressMapper.fromId("550e8400e29b41d4a716446655440000").getId()).isEqualTo("550e8400e29b41d4a716446655440000");
        assertThat(addressMapper.fromId(null)).isNull();
    }
}
