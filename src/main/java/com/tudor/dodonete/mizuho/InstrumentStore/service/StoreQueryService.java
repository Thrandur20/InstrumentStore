package com.tudor.dodonete.mizuho.InstrumentStore.service;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;

import java.util.List;

public interface StoreQueryService {
    List<StoreDTO> getAllStoreInfo();

    StoreDTO getStoreInfoById(long id);
}
