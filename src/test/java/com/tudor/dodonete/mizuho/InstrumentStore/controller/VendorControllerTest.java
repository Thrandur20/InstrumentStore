package com.tudor.dodonete.mizuho.InstrumentStore.controller;

import com.tudor.dodonete.mizuho.InstrumentStore.dto.VendorDTO;
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
public class VendorControllerTest extends AbstractControllerTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getAllVendors() throws Exception {
        String uri = "/vendor";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        VendorDTO[] vendorList = super.mapFromJSON(content, VendorDTO[].class);
        assertTrue(vendorList.length > 0);
    }

    @Test
    public void createNewVendor() throws Exception {
        String uri = "/vendor";
        VendorDTO vendorDTO = new VendorDTO(1L, "Vendor");

        String inputJson = super.mapToJSON(vendorDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);

        String content = mvcResult.getResponse().getContentAsString();
        VendorDTO vendor = super.mapFromJSON(content, VendorDTO.class);
        assertEquals("Vendor", vendor.getVendorName());
    }

    @Test
    public void getVendorById() throws Exception {
        String uri = "/vendor/51";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        VendorDTO vendor = super.mapFromJSON(content, VendorDTO.class);
        assertEquals(51L, vendor.getVendorId());
    }

    @Test
    public void deleteVendorById() throws Exception {
        String uri = "/vendor/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    public void updateVendor() throws Exception {
        long VENDOR_ID = 1L;
        String uri = "/vendor/" + VENDOR_ID;
        VendorDTO vendorDTO = new VendorDTO(VENDOR_ID, "Vendor2");
        String inputJson = super.mapToJSON(vendorDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        VendorDTO vendor = super.mapFromJSON(content, VendorDTO.class);
        assertEquals(VENDOR_ID, vendor.getVendorId());
    }
}