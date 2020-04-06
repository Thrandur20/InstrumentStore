package com.tudor.dodonete.mizuho.InstrumentStore.service;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.InstrumentDTO;

import java.util.List;

public interface InstrumentQueryService {
    List<InstrumentDTO> getAllInstruments();

    InstrumentDTO getInstrumentById(long id);
}
