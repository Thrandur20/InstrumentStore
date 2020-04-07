package com.tudor.dodonete.mizuho.InstrumentStore.controller;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.InstrumentDTO;
import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class DownstreamControllerTest extends AbstractControllerTest {
    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getAllPricesByVendor() throws Exception {
        String uri = "/downstream/prices/vendor/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        StoreDTO[] instrumentList = super.mapFromJSON(content, StoreDTO[].class);
        assertTrue(instrumentList.length > 0);
    }

    @Test
    public void getAllPricesByInstrument() throws Exception {
        String uri = "/downstream/prices/instrument/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        StoreDTO[] instrumentList = super.mapFromJSON(content, StoreDTO[].class);
        assertTrue(instrumentList.length > 0);
    }
}