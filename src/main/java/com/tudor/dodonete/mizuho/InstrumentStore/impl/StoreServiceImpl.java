package com.tudor.dodonete.mizuho.InstrumentStore.impl;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Instrument;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Store;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import com.tudor.dodonete.mizuho.InstrumentStore.exceptions.NoSuchResourceFoundException;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.InstrumentRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.StoreRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.VendorRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.service.StoreCommandService;
import com.tudor.dodonete.mizuho.InstrumentStore.service.StoreQueryService;
import com.tudor.dodonete.mizuho.InstrumentStore.utilis.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreQueryService, StoreCommandService {

    private StoreRepository storeRepository;
    private InstrumentRepository instrumentRepository;
    private VendorRepository vendorRepository;
    private DateUtility dateUtility;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository, DateUtility dateUtility) {
        this.storeRepository = storeRepository;
        this.dateUtility = dateUtility;
    }

    @Autowired
    public void setInstrumentRepository(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void addInformation(StoreDTO storeDTO) {
        Store store = new Store();
        verifyIfInstrumentExistsAndSave(storeDTO, store);

        verifyIfVendorExistsAndSave(storeDTO, store);

        store.setPrice(storeDTO.getPrice());
        store.setEntryDate(dateUtility.getCurrentDate());
        storeRepository.save(store);
    }

    @Override
    public void updateInformation(long id, StoreDTO storeDTO) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) {
            store.get().setPrice(storeDTO.getPrice());
            store.get().setEntryDate(dateUtility.getCurrentDate());
            verifyIfInstrumentExistsAndSave(storeDTO, store.get());
            verifyIfVendorExistsAndSave(storeDTO, store.get());
        } else {
            throw new NoSuchResourceFoundException("There is no Store Information for that id");
        }

        storeRepository.save(store.get());
    }

    @Override
    public void deleteInformation(long id) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) {
            storeRepository.delete(store.get());
        } else {
            throw new NoSuchResourceFoundException("There is no Store information to be deleted at that id");
        }
    }

    @Override
    public List<StoreDTO> getAllStoreInfo() {
        List<Store> storeList = storeRepository.findAll();
        List<StoreDTO> storeDtoList = new ArrayList<>();
        storeList.forEach(store -> storeDtoList.add(
                new StoreDTO(
                        store.getStoreId(),
                        store.getPrice(),
                        store.getEntryDate(),
                        store.getVendor().getVendorName(),
                        store.getInstrument().getInstrumentName())
        ));
        return storeDtoList;
    }

    @Override
    public StoreDTO getStoreInfoById(long id) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) {
            return new StoreDTO(
                    store.get().getStoreId(),
                    store.get().getPrice(),
                    store.get().getEntryDate(),
                    store.get().getVendor().getVendorName(),
                    store.get().getInstrument().getInstrumentName());
        } else {
            throw new NoSuchResourceFoundException("There is no Store information at that id");
        }
    }

    @Override
    public StoreDTO getStoreInfo(StoreDTO storeDTO, boolean hasId) {
        Optional<Vendor> foundVendor = vendorRepository.findOneByVendorName(storeDTO.getVendorName());
        Optional<Instrument> foundInstrument = instrumentRepository.findOneByInstrumentName(storeDTO.getInstrumentName());
        if (foundInstrument.isEmpty() || foundVendor.isEmpty()) {
            throw new NoSuchResourceFoundException("No Vendor or Instrument found for the given names");
        }

        Optional<Store> foundStore = hasId ? storeRepository.findById(storeDTO.getStoreId()) :
                storeRepository.findOneByInstrumentAndVendor(foundInstrument.get(), foundVendor.get());
        if (foundStore.isEmpty()) {
            throw new NoSuchResourceFoundException("No Store Information was found for the given id");
        }

        storeDTO.setStoreId(foundStore.get().getStoreId());
        storeDTO.setPrice(foundStore.get().getPrice());
        storeDTO.setEntryDate(foundStore.get().getEntryDate());
        storeDTO.setInstrumentName(foundInstrument.get().getInstrumentName());
        storeDTO.setVendorName(foundVendor.get().getVendorName());
        return storeDTO;
    }

    private void verifyIfVendorExistsAndSave(StoreDTO storeDTO, Store store) {
        if (!storeDTO.getVendorName().isEmpty()) {
            Optional<Vendor> vendor = vendorRepository.findOneByVendorName(storeDTO.getVendorName());
            if (vendor.isPresent()) {
                store.setVendor(vendor.get());
            } else {
                throw new NoSuchResourceFoundException("There is no Vendor with the given name");
            }
        }
    }

    private void verifyIfInstrumentExistsAndSave(StoreDTO storeDTO, Store store) {
        if (!storeDTO.getInstrumentName().isEmpty()) {
            Optional<Instrument> instrument = instrumentRepository.findOneByInstrumentName(storeDTO.getInstrumentName());
            if (instrument.isPresent()) {
                store.setInstrument(instrument.get());
            } else {
                throw new NoSuchResourceFoundException("There is no Instrument with the given name");
            }
        }
    }
}
