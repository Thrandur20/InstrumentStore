package com.tudor.dodonete.mizuho.InstrumentStore.impl;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.VendorDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import com.tudor.dodonete.mizuho.InstrumentStore.exceptions.NoSuchResourceFoundException;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class VendorServiceImplTest {
    @InjectMocks
    private VendorServiceImpl vendorService;

    @Mock
    private VendorRepository vendorRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllVendorsAndReturnList() {
        //Given
        Vendor vendor = createDefaultVendor();
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
        Vendor Vendor = createDefaultVendor();
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

    private Vendor createDefaultVendor() {
        Vendor vendor = new Vendor();
        vendor.setVendorId(1L);
        vendor.setVendorName("Vendor");
        return vendor;
    }

}