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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DownstreamServiceImplTest {
    @InjectMocks
    private DownstreamServiceImpl downstreamService;

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
    public void getPricesByVendorWhenVendorIsPresentAndReturnList() {
        //Given
        long VENDOR_ID = 1L;
        String VENDOR_NAME = "Vendor";
        Date DATE = createDefaultDate();
        Vendor vendor = createDefaultVendor(VENDOR_NAME);
        Store store = createDefaultVendorStore(VENDOR_NAME);
        store.setEntryDate(DATE);
        List<Store> storeList = Collections.singletonList(store);
        when(dateUtility.getCurrentDate()).thenReturn(DATE);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(vendor));
        when(storeRepository.findAllRecentByVendorId(VENDOR_ID, dateMinusThirtyDays(DATE))).thenReturn(storeList);
        downstreamService = new DownstreamServiceImpl(storeRepository, instrumentRepository, vendorRepository, dateUtility);
        //When
        List<StoreDTO> storeDTOList = downstreamService.getAllPricesByVendor(VENDOR_ID);
        //Then
        StoreDTO storeDTO = new StoreDTO(
                3L,
                BigDecimal.valueOf(21342123.123),
                DATE,
                VENDOR_NAME,
                "Instrument 1");
        List<StoreDTO> expectedList = Collections.singletonList(storeDTO);
        assertEquals(expectedList, storeDTOList);
    }

    @Test
    public void getPricesByVendorWhenVendorIsPresentButNoListIsReturned() {
        //Given
        long VENDOR_ID = 1L;
        String VENDOR_NAME = "Vendor";
        Date DATE = createDefaultDate();
        Vendor vendor = createDefaultVendor(VENDOR_NAME);
        List<Store> storeList = new ArrayList<>();
        when(dateUtility.getCurrentDate()).thenReturn(DATE);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(vendor));
        when(storeRepository.findAllRecentByVendorId(VENDOR_ID, dateMinusThirtyDays(DATE))).thenReturn(storeList);
        downstreamService = new DownstreamServiceImpl(storeRepository, instrumentRepository, vendorRepository, dateUtility);
        //When
        List<StoreDTO> storeDTOList = downstreamService.getAllPricesByVendor(VENDOR_ID);
        //Then
        assertEquals(storeDTOList.size(), 0);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getPricesByVendorThenThrowNoSuchResourceFoundException() {
        //Given
        long VENDOR_ID = 1L;
        Date DATE = createDefaultDate();
        when(dateUtility.getCurrentDate()).thenReturn(DATE);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.empty());
        downstreamService = new DownstreamServiceImpl(storeRepository, instrumentRepository, vendorRepository, dateUtility);
        //When
        downstreamService.getAllPricesByVendor(VENDOR_ID);
        //Then throws exception
    }

    @Test
    public void getPricesByInstrumentWhenInstrumentIsPresentAndReturnList() {
        //Given
        long INSTRUMENT_ID = 2L;
        String INSTRUMENT_NAME = "Instrument 1";
        Date DATE = createDefaultDate();
        Instrument instrument = createDefaultInstrument(INSTRUMENT_NAME);
        Store store = createDefaultInstrumentStore(INSTRUMENT_NAME);
        store.setEntryDate(DATE);
        List<Store> storeList = Collections.singletonList(store);
        when(dateUtility.getCurrentDate()).thenReturn(DATE);
        when(instrumentRepository.findById(INSTRUMENT_ID)).thenReturn(Optional.of(instrument));
        when(storeRepository.findAllRecentByInstrumentId(INSTRUMENT_ID, dateMinusThirtyDays(DATE))).thenReturn(storeList);
        downstreamService = new DownstreamServiceImpl(storeRepository, instrumentRepository, vendorRepository, dateUtility);
        //When
        List<StoreDTO> storeDTOList = downstreamService.getAllPricesByInstrument(INSTRUMENT_ID);
        //Then
        StoreDTO storeDTO = new StoreDTO(
                3L,
                BigDecimal.valueOf(21342123.123),
                DATE,
                "Vendor",
                INSTRUMENT_NAME);
        List<StoreDTO> expectedList = Collections.singletonList(storeDTO);
        assertEquals(expectedList, storeDTOList);
    }

    @Test
    public void getPricesByInstrumentWhenInstrumentIsPresentButNoListIsReturned() {
        //Given
        long INSTRUMENT_ID = 1L;
        String INSTRUMENT_NAME = "Instrument 1";
        Date DATE = createDefaultDate();
        Instrument instrument = createDefaultInstrument(INSTRUMENT_NAME);
        List<Store> storeList = new ArrayList<>();
        when(dateUtility.getCurrentDate()).thenReturn(DATE);
        when(instrumentRepository.findById(INSTRUMENT_ID)).thenReturn(Optional.of(instrument));
        when(storeRepository.findAllRecentByInstrumentId(INSTRUMENT_ID, dateMinusThirtyDays(DATE))).thenReturn(storeList);
        downstreamService = new DownstreamServiceImpl(storeRepository, instrumentRepository, vendorRepository, dateUtility);
        //When
        List<StoreDTO> storeDTOList = downstreamService.getAllPricesByInstrument(INSTRUMENT_ID);
        //Then
        assertEquals(storeDTOList.size(), 0);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getPricesByInstrumentThenThrowNoSuchResourceFoundException() {
        //Given
        long INSTRUMENT_ID = 1L;
        Date DATE = createDefaultDate();
        when(dateUtility.getCurrentDate()).thenReturn(DATE);
        when(instrumentRepository.findById(INSTRUMENT_ID)).thenReturn(Optional.empty());
        downstreamService = new DownstreamServiceImpl(storeRepository, instrumentRepository, vendorRepository, dateUtility);
        //When
        downstreamService.getAllPricesByInstrument(INSTRUMENT_ID);
        //Then throws exception
    }

    private Vendor createDefaultVendor(String vendorName) {
        Vendor vendor = new Vendor();
        vendor.setVendorId(1L);
        vendor.setVendorName(vendorName);
        return vendor;
    }

    private Store createDefaultVendorStore(String vendorName) {
        Store store = new Store();
        store.setPrice(BigDecimal.valueOf(21342123.123));
        store.setVendor(createDefaultVendor(vendorName));
        store.setInstrument(createDefaultInstrument("Instrument 1"));
        store.setStoreId(3L);
        return store;
    }

    private Store createDefaultInstrumentStore(String instrumentName) {
        Store store = new Store();
        store.setPrice(BigDecimal.valueOf(21342123.123));
        store.setVendor(createDefaultVendor("Vendor"));
        store.setInstrument(createDefaultInstrument(instrumentName));
        store.setStoreId(3L);
        return store;
    }

    private Date createDefaultDate() {
        return new GregorianCalendar(2020, Calendar.APRIL, 5).getTime();
    }

    private Date dateMinusThirtyDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        return calendar.getTime();
    }

    private Instrument createDefaultInstrument(String instrumentName) {
        Instrument instrument = new Instrument();
        instrument.setInstrumentName(instrumentName);
        instrument.setInstrumentId(2L);
        return instrument;
    }
}