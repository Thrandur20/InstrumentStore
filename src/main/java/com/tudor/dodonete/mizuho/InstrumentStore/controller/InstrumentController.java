package com.tudor.dodonete.mizuho.InstrumentStore.controller;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.InstrumentDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.service.InstrumentCommandService;
import com.tudor.dodonete.mizuho.InstrumentStore.service.InstrumentQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstrumentController {
    private InstrumentQueryService instrumentQueryService;
    private InstrumentCommandService instrumentCommandService;

    public InstrumentController(InstrumentQueryService instrumentQueryService, InstrumentCommandService instrumentCommandService) {
        this.instrumentQueryService = instrumentQueryService;
        this.instrumentCommandService = instrumentCommandService;
    }

    @RequestMapping(value = "/instrument", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InstrumentDTO> getAllInstruments() {
        return instrumentQueryService.getAllInstruments();
    }

    @RequestMapping(value = "/instrument", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InstrumentDTO createNewInstrument(@RequestBody @Validated InstrumentDTO instrument) {
        instrumentCommandService.createInstrument(instrument);
        return instrumentQueryService.getInstrument(instrument, false);
    }

    @RequestMapping(value = "/instrument/{instrumentId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InstrumentDTO getInstrumentById(@PathVariable long instrumentId) {
        return instrumentQueryService.getInstrumentById(instrumentId);
    }

    @RequestMapping(value = "/instrument/{instrumentId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument(@PathVariable long instrumentId) {
        instrumentCommandService.deleteInstrumentById(instrumentId);
    }

    @RequestMapping(value = "/instrument/{instrumentId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InstrumentDTO updateInstrument(@RequestBody @Validated InstrumentDTO instrumentDTO, @PathVariable long instrumentId) {
        instrumentCommandService.updateInstrument(instrumentId, instrumentDTO);
        return instrumentQueryService.getInstrument(instrumentDTO, true);
    }
}
