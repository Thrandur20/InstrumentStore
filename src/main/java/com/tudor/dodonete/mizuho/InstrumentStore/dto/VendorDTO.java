package com.tudor.dodonete.mizuho.InstrumentStore.dto;

import lombok.Data;

@Data
public class VendorDTO {
    private long vendorId;
    private String vendorName;

    public VendorDTO(long vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }
}
