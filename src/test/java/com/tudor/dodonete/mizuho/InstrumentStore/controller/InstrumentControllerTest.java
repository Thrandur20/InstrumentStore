package com.tudor.dodonete.mizuho.InstrumentStore.controller;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.InstrumentDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class InstrumentControllerTest extends AbstractControllerTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getInstrumentById() throws Exception {
        String uri = "/instrument/51";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        InstrumentDTO instrument = super.mapFromJSON(content, InstrumentDTO.class);
        assertEquals(51L, instrument.getInstrumentId());
    }

    @Test
    public void getAllInstruments() throws Exception {
        String uri = "/instrument";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        InstrumentDTO[] instrumentList = super.mapFromJSON(content, InstrumentDTO[].class);
        assertTrue(instrumentList.length > 0);
    }

    @Test
    public void deleteInstrumentById() throws Exception {
        String uri = "/instrument/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void createNewInstrument() throws Exception {
        String uri = "/instrument";
        InstrumentDTO instrumentDTO = new InstrumentDTO(1L, "Instrument");

        String inputJson = super.mapToJSON(instrumentDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }


    @Test
    public void updateInstrument() throws Exception {
        long INSTRUMENT_ID = 1L;
        String uri = "/instrument/" + INSTRUMENT_ID;
        InstrumentDTO instrumentDTO = new InstrumentDTO(INSTRUMENT_ID, "Instrument2");
        String inputJson = super.mapToJSON(instrumentDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}