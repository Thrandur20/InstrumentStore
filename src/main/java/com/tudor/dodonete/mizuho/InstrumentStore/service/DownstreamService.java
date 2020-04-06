package com.tudor.dodonete.mizuho.InstrumentStore.service;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;

import java.util.List;

public interface DownstreamService {
    List<StoreDTO> getAllPricesByVendor(long vendorId);

    List<StoreDTO> getAllPricesByInstrument(long instrumentId);
}
