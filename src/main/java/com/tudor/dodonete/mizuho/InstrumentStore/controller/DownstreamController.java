package com.tudor.dodonete.mizuho.InstrumentStore.controller;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.service.DownstreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/downstream")
public class DownstreamController {
    private DownstreamService downstreamService;

    @Autowired
    public DownstreamController(DownstreamService downstreamService) {
        this.downstreamService = downstreamService;
    }

    @RequestMapping(value = "/prices/vendor/{vendorId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<StoreDTO> getAllPricesByVendor(@PathVariable long vendorId) {
        return downstreamService.getAllPricesByVendor(vendorId);
    }

    @RequestMapping(value = "/prices/instrument/{instrumentId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<StoreDTO> getAllPricesByInstrument(@PathVariable long instrumentId) {
        return downstreamService.getAllPricesByInstrument(instrumentId);
    }

}
