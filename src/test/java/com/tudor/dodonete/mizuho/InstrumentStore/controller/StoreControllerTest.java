package com.tudor.dodonete.mizuho.InstrumentStore.controller;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.StoreDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class StoreControllerTest extends AbstractControllerTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getAllStores() throws Exception {
        String uri = "/store";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        StoreDTO[] vendorList = super.mapFromJSON(content, StoreDTO[].class);
        assertTrue(vendorList.length > 0);
    }

    @Test
    public void addInformationToStore() throws Exception {
        String uri = "/store";
        BigDecimal PRICE = BigDecimal.valueOf(1234567.123);
        StoreDTO storeDTO = new StoreDTO(
                1L,
                PRICE,
                new Date(),
                "Amazon",
                "Instrument1"
        );

        String inputJson = super.mapToJSON(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void getStoreById() throws Exception {
        String uri = "/store/101";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        StoreDTO store = super.mapFromJSON(content, StoreDTO.class);
        assertEquals(101L, store.getStoreId());
    }

    @Test
    public void deleteStoreInformation() throws Exception {
        String uri = "/store/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void updateInformationInStore() throws Exception {
        long STORE_ID = 51L;
        BigDecimal PRICE = BigDecimal.valueOf(1234567.123);
        String uri = "/store/" + STORE_ID;
        StoreDTO storeDTO = new StoreDTO(
                STORE_ID,
                PRICE,
                new Date(),
                "Amazon",
                "Instrument2");
        String inputJson = super.mapToJSON(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}