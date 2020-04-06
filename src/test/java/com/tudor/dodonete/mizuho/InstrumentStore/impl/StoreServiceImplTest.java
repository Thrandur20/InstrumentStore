package com.tudor.dodonete.mizuho.InstrumentStore.impl;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Instrument;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Store;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import com.tudor.dodonete.mizuho.InstrumentStore.exceptions.NoSuchResourceFoundException;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.InstrumentRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.StoreRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.VendorRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.utilis.DateUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class StoreServiceImplTest {
    @InjectMocks
    private StoreServiceImpl storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private InstrumentRepository instrumentRepository;

    @Mock
    private VendorRepository vendorRepository;

    @Mock
    private DateUtility dateUtility;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllInformationFromStoreAndReturnList() {
        //Given
        Date DEFAULT_DATE = createDefaultDate();
        Store store = createDefaultStore();
        store.setEntryDate(DEFAULT_DATE);
        List<Store> instrumentList = Collections.singletonList(store);
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findAll()).thenReturn(instrumentList);
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        //When
        List<StoreDTO> foundList = storeService.getAllStoreInfo();
        //Then
        StoreDTO storeDTO = new StoreDTO(
                3L,
                BigDecimal.valueOf(21342123.123),
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        List<StoreDTO> expectedList = Collections.singletonList(storeDTO);
        assertEquals(expectedList, foundList);
    }

    @Test
    public void getAllInformationFromStoreAndReturnEmptyList() {
        //Given
        Date DEFAULT_DATE = createDefaultDate();
        List<Store> storeList = new ArrayList<>();
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findAll()).thenReturn(storeList);
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        //When
        List<StoreDTO> foundList = storeService.getAllStoreInfo();
        //Then
        assertEquals(foundList.size(), 0);
    }

    @Test
    public void getStoreInfoByIdAndReturnResult() {
        //Given
        long STORE_ID = 3L;
        Date DEFAULT_DATE = createDefaultDate();
        Store store = createDefaultStore();
        store.setEntryDate(DEFAULT_DATE);
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.of(store));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        //When
        StoreDTO foundStoreDto = storeService.getStoreInfoById(STORE_ID);
        //Then
        StoreDTO expectedStoreDTO = new StoreDTO(
                3L,
                BigDecimal.valueOf(21342123.123),
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        assertEquals(expectedStoreDTO, foundStoreDto);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getStoreInfoByIdAndThrowException() {
        //Given
        long STORE_ID = 1L;
        Date DEFAULT_DATE = createDefaultDate();
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.empty());
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        //When
        storeService.getStoreInfoById(STORE_ID);
        //Then expected exception to be thrown
    }

    private Vendor createDefaultVendor() {
        Vendor vendor = new Vendor();
        vendor.setVendorId(1L);
        vendor.setVendorName("Vendor");
        return vendor;
    }

    private Instrument createDefaultInstrument() {
        Instrument instrument = new Instrument();
        instrument.setInstrumentName("Instrument 1");
        instrument.setInstrumentId(2L);
        return instrument;
    }

    private Store createDefaultStore() {
        Store store = new Store();
        store.setPrice(BigDecimal.valueOf(21342123.123));
        store.setVendor(createDefaultVendor());
        store.setInstrument(createDefaultInstrument());
        store.setStoreId(3L);
        return store;
    }

    private Date createDefaultDate() {
        return new GregorianCalendar(2020, Calendar.APRIL, 5).getTime();
    }
}