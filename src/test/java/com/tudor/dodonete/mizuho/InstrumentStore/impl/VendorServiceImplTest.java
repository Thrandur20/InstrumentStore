package com.tudor.dodonete.mizuho.InstrumentStore.impl;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.VendorDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import com.tudor.dodonete.mizuho.InstrumentStore.exceptions.NoSuchResourceFoundException;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.StoreRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class VendorServiceImplTest {
    @InjectMocks
    private VendorServiceImpl vendorService;

    @Mock
    private VendorRepository vendorRepository;

    @Mock
    private StoreRepository storeRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createVendor() {
        //Given
        long VENDOR_ID = 1L;
        String VENDOR_NAME = "Vendor";
        VendorDTO vendorDTO = new VendorDTO(VENDOR_ID, VENDOR_NAME);
        vendorService = new VendorServiceImpl(vendorRepository);
        //When
        vendorService.createVendor(vendorDTO);
        //Then
        Vendor expectedVendor = new Vendor();
        expectedVendor.setVendorName(VENDOR_NAME);
        verify(vendorRepository).save(expectedVendor);
    }

    @Test
    public void deleteVendorById() {
        //Given
        long VENDOR_ID = 1L;
        Vendor vendor = createDefaultVendor(VENDOR_ID);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(vendor));
        when(storeRepository.findAllByVendor(vendor)).thenReturn(new ArrayList<>());
        vendorService = new VendorServiceImpl(vendorRepository);
        vendorService.setStoreRepository(storeRepository);
        //When
        vendorService.deleteVendorById(VENDOR_ID);
        //Then
        verify(vendorRepository).delete(vendor);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void deleteVendorButThrowExceptionBecauseIdWasNotFound() {
        //Given
        long VENDOR_ID = 2L;
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.empty());
        vendorService = new VendorServiceImpl(vendorRepository);
        //When
        vendorService.deleteVendorById(VENDOR_ID);
        //Then exception is thrown
    }

    @Test
    public void updateVendorWithGivenInformation() {
        //Given
        long VENDOR_ID = 1L;
        String VENDOR_NAME = "Vendor2";
        Vendor defaultVendor = createDefaultVendor(VENDOR_ID);
        VendorDTO updatedInfoDTO = new VendorDTO(VENDOR_ID, VENDOR_NAME);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(defaultVendor));
        vendorService = new VendorServiceImpl(vendorRepository);
        //When
        vendorService.updateVendor(VENDOR_ID, updatedInfoDTO);
        //Then
        Vendor expectedVendor = new Vendor();
        expectedVendor.setVendorId(VENDOR_ID);
        expectedVendor.setVendorName(VENDOR_NAME);
        verify(vendorRepository).save(expectedVendor);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void updateVendorButThrowExceptionBecauseIdWasNotFound() {
        //Given
        long VENDOR_ID = 2L;
        VendorDTO vendorDTO = new VendorDTO(1L, "Vendor");
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.empty());
        vendorService = new VendorServiceImpl(vendorRepository);
        //When
        vendorService.updateVendor(VENDOR_ID, vendorDTO);
        //Then exception is thrown
    }

    @Test
    public void getAllVendorsAndReturnList() {
        //Given
        Vendor vendor = createDefaultVendor(1L);
        List<Vendor> vendorList = Collections.singletonList(vendor);
        when(vendorRepository.findAll()).thenReturn(vendorList);
        vendorService = new VendorServiceImpl(vendorRepository);
        //When
        List<VendorDTO> VendorDTOList = vendorService.getAllVendors();
        //Then
        VendorDTO VendorDTO = new VendorDTO(
                1L,
                "Vendor"
        );
        List<VendorDTO> expectedList = Collections.singletonList(VendorDTO);
        assertEquals(expectedList, VendorDTOList);
    }

    @Test
    public void getAllVendorsAndReturnEmptyList() {
        //Given
        List<Vendor> VendorList = new ArrayList<>();
        when(vendorRepository.findAll()).thenReturn(VendorList);
        vendorService = new VendorServiceImpl(vendorRepository);
        //When
        List<VendorDTO> VendorDTOList = vendorService.getAllVendors();
        //Then
        assertEquals(VendorDTOList.size(), 0);
    }

    @Test
    public void getVendorByIdWhenIdIsValid() {
        //Given
        long VENDOR_ID = 1L;
        Vendor Vendor = createDefaultVendor(VENDOR_ID);
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(java.util.Optional.of(Vendor));
        vendorService = new VendorServiceImpl(vendorRepository);
        //When
        VendorDTO foundVendorDTO = vendorService.getVendorById(VENDOR_ID);
        //Then
        VendorDTO expectedVendorDTO = new VendorDTO(1L, "Vendor");
        assertEquals(expectedVendorDTO, foundVendorDTO);

    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getVendorByIdWhenIdIsNotValidThenThrowException() {
        //Given
        long VENDOR_ID = 2L;
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.empty());
        vendorService = new VendorServiceImpl(vendorRepository);
        //When
        vendorService.getVendorById(VENDOR_ID);
        //Then throw exception
    }

    private Vendor createDefaultVendor(long vendorId) {
        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorId);
        vendor.setVendorName("Vendor");
        return vendor;
    }

}