package com.tudor.dodonete.mizuho.InstrumentStore.service;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.InstrumentDTO;

public interface InstrumentCommandService {
    void createInstrument(InstrumentDTO instrument);

    void deleteInstrumentById(long id);

    void updateInstrument(long id, InstrumentDTO instrumentDTO);
}
