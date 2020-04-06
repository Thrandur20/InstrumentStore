package com.tudor.dodonete.mizuho.InstrumentStore.impl;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Instrument;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Store;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import com.tudor.dodonete.mizuho.InstrumentStore.exceptions.NoSuchResourceFoundException;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.InstrumentRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.StoreRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.VendorRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.service.DownstreamService;
import com.tudor.dodonete.mizuho.InstrumentStore.utilis.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DownstreamServiceImpl implements DownstreamService {
    StoreRepository storeRepository;
    InstrumentRepository instrumentRepository;
    VendorRepository vendorRepository;
    DateUtility dateUtility;

    @Autowired
    public DownstreamServiceImpl(StoreRepository storeRepository, InstrumentRepository instrumentRepository,
                                 VendorRepository vendorRepository, DateUtility dateUtility) {
        this.storeRepository = storeRepository;
        this.instrumentRepository = instrumentRepository;
        this.vendorRepository = vendorRepository;
        this.dateUtility = dateUtility;
    }

    @Override
    public List<StoreDTO> getAllPricesByVendor(long vendorId) {
        List<StoreDTO> storeDTOList = new ArrayList<>();
        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
        if (vendor.isPresent()) {
            List<Store> storeList = storeRepository.findAllRecentByVendorId(vendor.get().getVendorId(), getCurrentDateMinusThirtyDays(dateUtility.getCurrentDate()));
            mapStoreToStoreDTOList(storeDTOList, storeList);
        } else {
            throw new NoSuchResourceFoundException("The Vendor with the given name cannot be found");
        }
        return storeDTOList;
    }

    @Override
    public List<StoreDTO> getAllPricesByInstrument(long instrumentId) {
        List<StoreDTO> storeDTOList = new ArrayList<>();
        Optional<Instrument> instrument = instrumentRepository.findById(instrumentId);
        if (instrument.isPresent()) {
            List<Store> storeList = storeRepository.findAllRecentByInstrumentId(instrument.get().getInstrumentId(), getCurrentDateMinusThirtyDays(dateUtility.getCurrentDate()));
            mapStoreToStoreDTOList(storeDTOList, storeList);
        } else {
            throw new NoSuchResourceFoundException("The Instrument with the given name cannot be found");
        }
        return storeDTOList;
    }

    private void mapStoreToStoreDTOList(List<StoreDTO> storeDTOList, List<Store> storeList) {
        storeList.forEach(store -> {
            storeDTOList.add(
                    new StoreDTO(
                            store.getStoreId(),
                            store.getPrice(),
                            store.getEntryDate(),
                            store.getVendor().getVendorName(),
                            store.getInstrument().getInstrumentName()
                    )
            );
        });
    }

    private Date getCurrentDateMinusThirtyDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        return calendar.getTime();
    }
}

