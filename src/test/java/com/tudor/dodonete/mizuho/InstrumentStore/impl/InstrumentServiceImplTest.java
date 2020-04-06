package com.tudor.dodonete.mizuho.InstrumentStore.impl;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.InstrumentDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.entity.Instrument;
import com.tudor.dodonete.mizuho.InstrumentStore.exceptions.NoSuchResourceFoundException;
import com.tudor.dodonete.mizuho.InstrumentStore.repository.InstrumentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class InstrumentServiceImplTest {
    @InjectMocks
    private InstrumentServiceImpl instrumentService;

    @Mock
    private InstrumentRepository instrumentRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllInstrumentsAndReturnList() {
        //Given
        Instrument instrument = createDefaultInstrument();
        List<Instrument> instrumentList = Collections.singletonList(instrument);
        when(instrumentRepository.findAll()).thenReturn(instrumentList);
        instrumentService = new InstrumentServiceImpl(instrumentRepository);
        //When
        List<InstrumentDTO> instrumentDTOList = instrumentService.getAllInstruments();
        //Then
        InstrumentDTO instrumentDTO = new InstrumentDTO(
                1L,
                "Instrument"
        );
        List<InstrumentDTO> expectedList = Collections.singletonList(instrumentDTO);
        assertEquals(expectedList, instrumentDTOList);
    }

    @Test
    public void getAllInstrumentsAndReturnEmptyList() {
        //Given
        List<Instrument> instrumentList = new ArrayList<>();
        when(instrumentRepository.findAll()).thenReturn(instrumentList);
        instrumentService = new InstrumentServiceImpl(instrumentRepository);
        //When
        List<InstrumentDTO> instrumentDTOList = instrumentService.getAllInstruments();
        //Then
        assertEquals(instrumentDTOList.size(), 0);
    }

    @Test
    public void getInstrumentByIdWhenIdIsValid() {
        //Given
        long INSTRUMENT_ID = 1L;
        Instrument instrument = createDefaultInstrument();
        when(instrumentRepository.findById(INSTRUMENT_ID)).thenReturn(java.util.Optional.of(instrument));
        instrumentService = new InstrumentServiceImpl(instrumentRepository);
        //When
        InstrumentDTO foundInstrumentDTO = instrumentService.getInstrumentById(INSTRUMENT_ID);
        //Then
        InstrumentDTO expectedInstrumentDTO = new InstrumentDTO(1L, "Instrument");
        assertEquals(expectedInstrumentDTO, foundInstrumentDTO);

    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void getInstrumentByIdWhenIdIsNotValidThenThrowException(){
        //Given
        long INSTRUMENT_ID = 2L;
        when(instrumentRepository.findById(INSTRUMENT_ID)).thenReturn(Optional.empty());
        instrumentService = new InstrumentServiceImpl(instrumentRepository);
        //When
        instrumentService.getInstrumentById(INSTRUMENT_ID);
        //Then throw exception
    }

    private Instrument createDefaultInstrument() {
        Instrument instrument = new Instrument();
        instrument.setInstrumentId(1L);
        instrument.setInstrumentName("Instrument");
        return instrument;
    }
}