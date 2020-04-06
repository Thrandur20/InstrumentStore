package com.tudor.dodonete.mizuho.InstrumentStore.service;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;

public interface StoreCommandService {
    void addInformation(StoreDTO storeDTO);

    void updateInformation(long id, StoreDTO storeDTO);

    void deleteInformation(long id);
}
