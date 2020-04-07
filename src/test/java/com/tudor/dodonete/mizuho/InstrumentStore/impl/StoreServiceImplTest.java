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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    private static final BigDecimal PRICE = BigDecimal.valueOf(21342123.123);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addStoreInformation() {
        //Given
        long STORE_ID = 1L;
        Date DEFAULT_DATE = createDefaultDate();
        StoreDTO storeDTO = new StoreDTO(
                STORE_ID,
                PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(vendorRepository.findOneByVendorName("Vendor"))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(instrumentRepository.findOneByInstrumentName("Instrument 1"))
                .thenReturn(Optional.of(createDefaultInstrument()));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setInstrumentRepository(instrumentRepository);
        storeService.setVendorRepository(vendorRepository);
        //When
        storeService.addInformation(storeDTO);
        //Then
        Store expectedStoreInformation = new Store();
        expectedStoreInformation.setPrice(PRICE);
        expectedStoreInformation.setEntryDate(DEFAULT_DATE);
        expectedStoreInformation.setVendor(createDefaultVendor());
        expectedStoreInformation.setInstrument(createDefaultInstrument());
        verify(storeRepository).save(expectedStoreInformation);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void addStoreInformationButThrowExceptionBecauseVendorWasNotFound() {
        //Given
        long STORE_ID = 2L;
        Date DEFAULT_DATE = createDefaultDate();
        StoreDTO storeDTO = new StoreDTO(
                1L,
                PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID))
                .thenReturn(Optional.of(createDefaultStore()));
        when(vendorRepository.findOneByVendorName("Vendor2")).thenReturn(Optional.empty());
        when(instrumentRepository.findOneByInstrumentName("Instrument 1"))
                .thenReturn(Optional.of(createDefaultInstrument()));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setInstrumentRepository(instrumentRepository);
        storeService.setVendorRepository(vendorRepository);
        //When
        storeService.addInformation(storeDTO);
        //Then exception is thrown
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void addStoreInformationButThrowExceptionBecauseInstrumentWasNotFound() {
        //Given
        long STORE_ID = 2L;
        Date DEFAULT_DATE = createDefaultDate();
        StoreDTO storeDTO = new StoreDTO(
                1L,
                PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID))
                .thenReturn(Optional.of(createDefaultStore()));
        when(vendorRepository.findOneByVendorName("Vendor"))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(instrumentRepository.findOneByInstrumentName("Instrument2"))
                .thenReturn(Optional.empty());
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setInstrumentRepository(instrumentRepository);
        storeService.setVendorRepository(vendorRepository);
        //When
        storeService.addInformation(storeDTO);
        //Then exception is thrown
    }

    @Test
    public void deleteStoreInformationById() {
        //Given
        long STORE_ID = 1L;
        Date DEFAULT_DATE = createDefaultDate();
        Store store = createDefaultStore();
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.of(store));
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        //When
        storeService.deleteInformation(STORE_ID);
        //Then
        verify(storeRepository).delete(store);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void deleteStoreInformationButThrowExceptionBecauseIdWasNotFound() {
        //Given
        long STORE_ID = 2L;
        Date DEFAULT_DATE = createDefaultDate();
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.empty());
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        //When
        storeService.deleteInformation(STORE_ID);
        //Then exception is thrown
    }

    @Test
    public void updateStoreWithGivenInformation() {
        //Given
        long STORE_ID = 3L;
        BigDecimal NEW_PRICE = BigDecimal.valueOf(132465.123);
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        defaultStore.setEntryDate(DEFAULT_DATE);
        StoreDTO updatedInfoDTO = new StoreDTO(
                STORE_ID,
                NEW_PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1");
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.of(defaultStore));
        when(vendorRepository.findOneByVendorName("Vendor"))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(instrumentRepository.findOneByInstrumentName("Instrument 1"))
                .thenReturn(Optional.of(createDefaultInstrument()));
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        storeService.updateInformation(STORE_ID, updatedInfoDTO);
        //Then
        Store expectedStore = new Store();
        expectedStore.setStoreId(STORE_ID);
        expectedStore.setPrice(NEW_PRICE);
        expectedStore.setEntryDate(DEFAULT_DATE);
        expectedStore.setVendor(createDefaultVendor());
        expectedStore.setInstrument(createDefaultInstrument());
        verify(storeRepository).save(expectedStore);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void updateStoreInformationButThrowExceptionBecauseIdWasNotFound() {
        //Given
        long STORE_ID = 2L;
        Date DEFAULT_DATE = createDefaultDate();
        StoreDTO storeDTO = new StoreDTO(
                1L,
                PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument1"
        );
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.empty());
        when(vendorRepository.findOneByVendorName("Vendor"))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(instrumentRepository.findOneByInstrumentName("Instrument 1"))
                .thenReturn(Optional.of(createDefaultInstrument()));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setInstrumentRepository(instrumentRepository);
        storeService.setVendorRepository(vendorRepository);
        //When
        storeService.updateInformation(STORE_ID, storeDTO);
        //Then exception is thrown
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void updateStoreInformationButThrowExceptionBecauseVendorWasNotFound() {
        //Given
        long STORE_ID = 2L;
        Date DEFAULT_DATE = createDefaultDate();
        StoreDTO storeDTO = new StoreDTO(
                1L,
                PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID))
                .thenReturn(Optional.of(createDefaultStore()));
        when(vendorRepository.findOneByVendorName("Vendor2")).thenReturn(Optional.empty());
        when(instrumentRepository.findOneByInstrumentName("Instrument 1"))
                .thenReturn(Optional.of(createDefaultInstrument()));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setInstrumentRepository(instrumentRepository);
        storeService.setVendorRepository(vendorRepository);
        //When
        storeService.updateInformation(STORE_ID, storeDTO);
        //Then exception is thrown
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void updateStoreInformationButThrowExceptionBecauseInstrumentWasNotFound() {
        //Given
        long STORE_ID = 2L;
        Date DEFAULT_DATE = createDefaultDate();
        StoreDTO storeDTO = new StoreDTO(
                1L,
                PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID))
                .thenReturn(Optional.of(createDefaultStore()));
        when(vendorRepository.findOneByVendorName("Vendor"))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(instrumentRepository.findOneByInstrumentName("Instrument2"))
                .thenReturn(Optional.empty());
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setInstrumentRepository(instrumentRepository);
        storeService.setVendorRepository(vendorRepository);
        //When
        storeService.updateInformation(STORE_ID, storeDTO);
        //Then exception is thrown
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
                PRICE,
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
                PRICE,
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


    @Test
    public void getStoreInformationAfterUpdateAndReturnValue() {
        //Given
        long STORE_ID = 3L;
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        defaultStore.setEntryDate(DEFAULT_DATE);
        StoreDTO givenStoreEntry = new StoreDTO(
                defaultStore.getStoreId(),
                defaultStore.getPrice(),
                defaultStore.getEntryDate(),
                defaultStore.getVendor().getVendorName(),
                defaultStore.getInstrument().getInstrumentName()
        );
        when(instrumentRepository.findOneByInstrumentName(defaultStore.getInstrument().getInstrumentName()))
                .thenReturn(Optional.of(createDefaultInstrument()));
        when(vendorRepository.findOneByVendorName(defaultStore.getVendor().getVendorName()))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.of(defaultStore));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        StoreDTO foundStore = storeService.getStoreInfo(givenStoreEntry, true);
        //Then
        StoreDTO expectedDto = new StoreDTO(
                STORE_ID,
                PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        assertEquals(expectedDto, foundStore);
    }

    @Test
    public void getStoreInformationAfterInsertAndReturnValue() {
        //Given
        long STORE_ID = 3L;
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        defaultStore.setEntryDate(DEFAULT_DATE);
        StoreDTO givenStoreEntry = new StoreDTO(
                defaultStore.getStoreId(),
                defaultStore.getPrice(),
                defaultStore.getEntryDate(),
                defaultStore.getVendor().getVendorName(),
                defaultStore.getInstrument().getInstrumentName()
        );
        when(instrumentRepository.findOneByInstrumentName(defaultStore.getInstrument().getInstrumentName()))
                .thenReturn(Optional.of(createDefaultInstrument()));
        when(vendorRepository.findOneByVendorName(defaultStore.getVendor().getVendorName()))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findOneByInstrumentAndVendor(any(Instrument.class), any(Vendor.class)))
                .thenReturn(Optional.of(defaultStore));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        StoreDTO foundStore = storeService.getStoreInfo(givenStoreEntry, false);
        //Then
        StoreDTO expectedDto = new StoreDTO(
                STORE_ID,
                PRICE,
                DEFAULT_DATE,
                "Vendor",
                "Instrument 1"
        );
        assertEquals(expectedDto, foundStore);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getStoreAfterUpdateInformationButReturnException() {
        //Given
        long STORE_ID = 1L;
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        defaultStore.setEntryDate(DEFAULT_DATE);
        StoreDTO givenStoreEntry = new StoreDTO(
                defaultStore.getStoreId(),
                defaultStore.getPrice(),
                defaultStore.getEntryDate(),
                defaultStore.getVendor().getVendorName(),
                defaultStore.getInstrument().getInstrumentName()
        );
        when(instrumentRepository.findOneByInstrumentName(defaultStore.getInstrument().getInstrumentName()))
                .thenReturn(Optional.of(createDefaultInstrument()));
        when(vendorRepository.findOneByVendorName(defaultStore.getVendor().getVendorName()))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.empty());
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        storeService.getStoreInfo(givenStoreEntry, true);
        //Then throw exception
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getStoreAfterUpdateButNoVendorFoundInformationButReturnException() {
        //Given
        long STORE_ID = 1L;
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        StoreDTO givenStoreEntry = new StoreDTO(
                defaultStore.getStoreId(),
                defaultStore.getPrice(),
                defaultStore.getEntryDate(),
                defaultStore.getVendor().getVendorName(),
                defaultStore.getInstrument().getInstrumentName()
        );
        when(instrumentRepository.findOneByInstrumentName(defaultStore.getInstrument().getInstrumentName()))
                .thenReturn(Optional.of(createDefaultInstrument()));
        when(vendorRepository.findOneByVendorName(defaultStore.getVendor().getVendorName()))
                .thenReturn(Optional.empty());
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.of(defaultStore));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        storeService.getStoreInfo(givenStoreEntry, true);
        //Then throw exception
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getStoreAfterUpdateButNoInstrumentFoundInformationButReturnException() {
        //Given
        long STORE_ID = 1L;
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        StoreDTO givenStoreEntry = new StoreDTO(
                defaultStore.getStoreId(),
                defaultStore.getPrice(),
                defaultStore.getEntryDate(),
                defaultStore.getVendor().getVendorName(),
                defaultStore.getInstrument().getInstrumentName()
        );
        when(instrumentRepository.findOneByInstrumentName(defaultStore.getInstrument().getInstrumentName()))
                .thenReturn(Optional.empty());
        when(vendorRepository.findOneByVendorName(defaultStore.getVendor().getVendorName()))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.of(defaultStore));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        storeService.getStoreInfo(givenStoreEntry, true);
        //Then throw exception
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getStoreAfterInsertInformationButReturnException() {
        //Given
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        StoreDTO givenStoreEntry = new StoreDTO(
                defaultStore.getStoreId(),
                defaultStore.getPrice(),
                defaultStore.getEntryDate(),
                defaultStore.getVendor().getVendorName(),
                defaultStore.getInstrument().getInstrumentName()
        );
        when(instrumentRepository.findOneByInstrumentName(defaultStore.getInstrument().getInstrumentName()))
                .thenReturn(Optional.of(createDefaultInstrument()));
        when(vendorRepository.findOneByVendorName(defaultStore.getVendor().getVendorName()))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findOneByInstrumentAndVendor(any(Instrument.class), any(Vendor.class)))
                .thenReturn(Optional.empty());
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        storeService.getStoreInfo(givenStoreEntry, false);
        //Then throw exception
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getStoreAfterInsertButNoVendorFoundInformationButReturnException() {
        //Given
        long STORE_ID = 1L;
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        StoreDTO givenStoreEntry = new StoreDTO(
                defaultStore.getStoreId(),
                defaultStore.getPrice(),
                defaultStore.getEntryDate(),
                defaultStore.getVendor().getVendorName(),
                defaultStore.getInstrument().getInstrumentName()
        );
        when(instrumentRepository.findOneByInstrumentName(defaultStore.getInstrument().getInstrumentName()))
                .thenReturn(Optional.of(createDefaultInstrument()));
        when(vendorRepository.findOneByVendorName(defaultStore.getVendor().getVendorName()))
                .thenReturn(Optional.empty());
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findOneByInstrumentAndVendor(any(Instrument.class), any(Vendor.class)))
                .thenReturn(Optional.of(defaultStore));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        storeService.getStoreInfo(givenStoreEntry, false);
        //Then throw exception
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getStoreAfterInsertButNoInstrumentFoundInformationButReturnException() {
        //Given
        long STORE_ID = 1L;
        Date DEFAULT_DATE = createDefaultDate();
        Store defaultStore = createDefaultStore();
        StoreDTO givenStoreEntry = new StoreDTO(
                defaultStore.getStoreId(),
                defaultStore.getPrice(),
                defaultStore.getEntryDate(),
                defaultStore.getVendor().getVendorName(),
                defaultStore.getInstrument().getInstrumentName()
        );
        when(instrumentRepository.findOneByInstrumentName(defaultStore.getInstrument().getInstrumentName()))
                .thenReturn(Optional.empty());
        when(vendorRepository.findOneByVendorName(defaultStore.getVendor().getVendorName()))
                .thenReturn(Optional.of(createDefaultVendor()));
        when(dateUtility.getCurrentDate()).thenReturn(DEFAULT_DATE);
        when(storeRepository.findOneByInstrumentAndVendor(any(Instrument.class), any(Vendor.class)))
                .thenReturn(Optional.of(defaultStore));
        storeService = new StoreServiceImpl(storeRepository, dateUtility);
        storeService.setVendorRepository(vendorRepository);
        storeService.setInstrumentRepository(instrumentRepository);
        //When
        storeService.getStoreInfo(givenStoreEntry, false);
        //Then throw exception
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
        store.setPrice(PRICE);
        store.setVendor(createDefaultVendor());
        store.setInstrument(createDefaultInstrument());
        store.setStoreId(3L);
        return store;
    }

    private Date createDefaultDate() {
        return new GregorianCalendar(2020, Calendar.APRIL, 5).getTime();
    }
}