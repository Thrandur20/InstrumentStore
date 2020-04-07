package com.tudor.dodonete.mizuho.InstrumentStore.impl;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.InstrumentDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Instrument;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Store;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import com.tudor.dodonete.mizuho.InstrumentStore.exceptions.NoSuchResourceFoundException;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.InstrumentRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.StoreRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.service.InstrumentCommandService;
import com.tudor.dodonete.mizuho.InstrumentStore.service.InstrumentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("instrumentService")
public class InstrumentServiceImpl implements InstrumentQueryService, InstrumentCommandService {

    private InstrumentRepository instrumentRepository;
    private StoreRepository storeRepository;

    @Autowired
    public InstrumentServiceImpl(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @Autowired
    public void setStoreRepository(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public void createInstrument(InstrumentDTO instrument) {
        Instrument newInstrument = new Instrument();
        newInstrument.setInstrumentName(instrument.getInstrumentName());
        instrumentRepository.save(newInstrument);
    }

    @Override
    public void deleteInstrumentById(long id) {
        Optional<Instrument> instrument = instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            List<Store> toBeDeletedFromStore = storeRepository.findAllByInstrument(instrument.get());
            storeRepository.deleteAll(toBeDeletedFromStore);
            instrumentRepository.delete(instrument.get());
        } else {
            throw new NoSuchResourceFoundException("No instrument was found for the given id");
        }
    }

    @Override
    public void updateInstrument(long id, InstrumentDTO instrumentDTO) {
        Optional<Instrument> instrument = instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            instrument.get().setInstrumentName(instrumentDTO.getInstrumentName());
        } else {
            throw new NoSuchResourceFoundException("No instrument was found for the given id");
        }
        instrumentRepository.save(instrument.get());
    }

    @Override
    public List<InstrumentDTO> getAllInstruments() {
        List<Instrument> instrumentList = instrumentRepository.findAll();
        List<InstrumentDTO> instrumentDtoList = new ArrayList<>();
        instrumentList.forEach(ins -> instrumentDtoList.add(
                new InstrumentDTO(
                        ins.getInstrumentId(),
                        ins.getInstrumentName())
        ));
        return instrumentDtoList;
    }

    @Override
    public InstrumentDTO getInstrumentById(long id) {
        Optional<Instrument> instrument = instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            return new InstrumentDTO(
                    instrument.get().getInstrumentId(),
                    instrument.get().getInstrumentName());
        } else {
            throw new NoSuchResourceFoundException("No instrument was found for the given id");
        }
    }

    @Override
    public InstrumentDTO getInstrument(InstrumentDTO instrumentDTO, boolean hasId) {
        Optional<Instrument> foundInstrument = hasId ? instrumentRepository.findById(instrumentDTO.getInstrumentId()) : instrumentRepository.findOneByInstrumentName(instrumentDTO.getInstrumentName());
        if (foundInstrument.isEmpty()) {
            throw new NoSuchResourceFoundException("No vendor was found for the given id");
        }
        instrumentDTO.setInstrumentId(foundInstrument.get().getInstrumentId());
        instrumentDTO.setInstrumentName(foundInstrument.get().getInstrumentName());
        return instrumentDTO;
    }
}
