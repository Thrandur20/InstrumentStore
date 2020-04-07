package com.tudor.dodonete.mizuho.InstrumentStore.service;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.VendorDTO;

import java.util.List;

public interface VendorQueryService {
    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(long id);

    VendorDTO getVendor(VendorDTO vendor, boolean hasId);
}
