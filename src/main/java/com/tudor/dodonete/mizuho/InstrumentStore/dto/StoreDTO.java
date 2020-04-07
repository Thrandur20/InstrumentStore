package com.tudor.dodonete.mizuho.InstrumentStore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StoreDTO {
    private long storeId;
    private BigDecimal price;
    private Date entryDate;
    private String vendorName;
    private String instrumentName;

    public StoreDTO() {
    }

    public StoreDTO(long storeId, BigDecimal price, Date entryDate, String vendorName, String instrumentName) {
        this.storeId = storeId;
        this.price = price;
        this.entryDate = entryDate;
        this.vendorName = vendorName;
        this.instrumentName = instrumentName;
    }
}
