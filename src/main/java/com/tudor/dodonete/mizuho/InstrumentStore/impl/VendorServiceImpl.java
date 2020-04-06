package com.tudor.dodonete.mizuho.InstrumentStore.impl;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.VendorDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Vendor;
import com.tudor.dodonete.mizuho.InstrumentStore.exceptions.NoSuchResourceFoundException;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.VendorRepository;
import com.tudor.dodonete.mizuho.InstrumentStore.service.VendorCommandService;
import com.tudor.dodonete.mizuho.InstrumentStore.service.VendorQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("vendorService")
public class VendorServiceImpl implements VendorCommandService, VendorQueryService {

    private VendorRepository vendorRepository;

    @Autowired
    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void createVendor(VendorDTO vendor) {
        Vendor newVendor = new Vendor();
        newVendor.setVendorName(vendor.getVendorName());
        vendorRepository.save(newVendor);
    }

    @Override
    public void deleteVendorById(long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isPresent()) {
            vendorRepository.delete(vendor.get());
        } else {
            throw new NoSuchResourceFoundException("No vendor was found for the given id");
        }
    }

    @Override
    public void updateVendor(long id, VendorDTO vendorDTO) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isPresent()) {
            vendor.get().setVendorName(vendorDTO.getVendorName());
        } else {
            throw new NoSuchResourceFoundException("No vendor was found for the given id");
        }
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        List<Vendor> vendorList = vendorRepository.findAll();
        List<VendorDTO> vendorDtoList = new ArrayList<>();
        vendorList.forEach(vendor -> {
            vendorDtoList.add(
                    new VendorDTO(
                            vendor.getVendorId(),
                            vendor.getVendorName())
            );
        });
        return vendorDtoList;
    }

    @Override
    public VendorDTO getVendorById(long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isPresent()) {
            return new VendorDTO(
                    vendor.get().getVendorId(),
                    vendor.get().getVendorName()
            );
        } else {
            throw new NoSuchResourceFoundException("No vendor was found for the given id");
        }
    }
}
