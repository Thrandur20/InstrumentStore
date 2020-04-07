package com.tudor.dodonete.mizuho.InstrumentStore.controller;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.VendorDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.service.VendorQueryService;
import com.tudor.dodonete.mizuho.InstrumentStore.service.VendorCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VendorController {
    private VendorQueryService vendorQueryService;
    private VendorCommandService vendorCommandService;

    @Autowired
    public VendorController(VendorQueryService vendorQueryService, VendorCommandService vendorCommandService) {
        this.vendorQueryService = vendorQueryService;
        this.vendorCommandService = vendorCommandService;
    }

    @RequestMapping(value = {"/", "/vendor"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<VendorDTO> getAllVendors() {
        return vendorQueryService.getAllVendors();
    }

    @RequestMapping(value = "/vendor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewVendor(@RequestBody @Validated VendorDTO vendor) {
        vendorCommandService.createVendor(vendor);
    }

    @RequestMapping(value = "/vendor/{vendorId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable long vendorId) {
        return vendorQueryService.getVendorById(vendorId);
    }

    @RequestMapping(value = "/vendor/{vendorId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable long vendorId) {
        vendorCommandService.deleteVendorById(vendorId);
    }

    @RequestMapping(value = "/vendor/{vendorId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateVendor(@RequestBody @Validated VendorDTO vendorDTO, @PathVariable long vendorId) {
        vendorCommandService.updateVendor(vendorId, vendorDTO);
    }
}
