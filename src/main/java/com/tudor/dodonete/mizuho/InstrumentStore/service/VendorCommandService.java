package com.tudor.dodonete.mizuho.InstrumentStore.service;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.VendorDTO;

public interface VendorCommandService {
    void createVendor(VendorDTO vendor);

    void deleteVendorById(long id);

    void updateVendor(long id, VendorDTO vendorDTO);
}
