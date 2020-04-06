package com.tudor.dodonete.mizuho.InstrumentStore.controller;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.service.StoreCommandService;
import com.tudor.dodonete.mizuho.InstrumentStore.service.StoreQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoreController {
    private StoreQueryService storeQueryService;
    private StoreCommandService storeCommandService;

    @Autowired
    public StoreController(StoreQueryService storeQueryService, StoreCommandService storeCommandService) {
        this.storeQueryService = storeQueryService;
        this.storeCommandService = storeCommandService;
    }

    @RequestMapping(value = "/store", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<StoreDTO> getAllStores() {
        return storeQueryService.getAllStoreInfo();
    }

    @RequestMapping(value = "/store/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public StoreDTO getStoreById(@PathVariable Long id) {
        return storeQueryService.getStoreInfoById(id);
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addInformationToStore(@RequestBody @Validated StoreDTO storeDTO) {
        storeCommandService.addInformation(storeDTO);
    }

    @RequestMapping(value = "/store/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateInformationInStore(@PathVariable long id, @RequestBody StoreDTO storeDTO) {
        storeCommandService.updateInformation(id, storeDTO);
    }

    @RequestMapping(value = "/store/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteStoreInformation(@PathVariable long id) {
        storeCommandService.deleteInformation(id);
    }
}