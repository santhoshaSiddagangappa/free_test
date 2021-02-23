package com.freenow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.DataManipulationTest;
import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.service.driver.DriverService;
import com.freenow.service.drivercar.DriverCarService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class DriverControllerTest extends DataManipulationTest
{

    private static final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    @Mock
    private DriverService driverService;

    @Mock
    private DriverCarService driverCarService;

    @InjectMocks
    private DriverController driverController;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(DriverController.class);
    }


    @Before
    public void init()
    {
        mvc = MockMvcBuilders.standaloneSetup(driverController).dispatchOptions(true).build();
    }


    @Test
    public void testSelectCarByDriver() throws Exception
    {
        DriverDTO driverData = getDriverDTO();

        doReturn(driverData).when(driverService).selectCarByDriver(any(Long.class), any(Long.class));

        driverController.selectCarByDriver(1L, 1L);

        MvcResult result =
                mvc
                        .perform(
                                post("/v1/drivers/select")
                                        .param("driverId", "1")
                                        .param("carId", "1"))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        final String responseBody = result.getResponse().getContentAsString();

        Assert.assertTrue(responseBody.contains("test"));

    }


    @Test
    public void testDeSelectCarByDriver() throws Exception
    {
        doNothing().when(driverService).deSelectCarByDriver(any(Long.class), any(Long.class));
        driverController.deSelectCarByDriver(1L, 1L);
        MvcResult result =
                mvc
                        .perform(
                                post("/v1/drivers/select")
                                        .param("driverId", "1")
                                        .param("carId", "1"))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void testGetDriver() throws Exception
    {
        DriverDO driverData = getDriver();

        doReturn(driverData).when(driverService).find(any(Long.class));
        driverController.getDriver(1L);
        MvcResult result =
                mvc
                        .perform(get("/v1/drivers/{driverId}", 1))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("test"));
    }


    @Test
    public void testUpdateLocation() throws Exception
    {
        doNothing().when(driverService).updateLocation(any(Long.class), any(Double.class), any(Double.class));
        driverController.updateLocation(1L, 99, 99);
        MvcResult result =
                mvc
                        .perform(
                                put("/v1/drivers/{driverId}", 1)
                                        .param("longitude", "50").param("latitude", "60"))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void testCreateDriver() throws Exception
    {
        DriverDTO driverData = getDriverDTO();
        DriverDO driverDO = DriverMapper.makeDriverDO(driverData);

        String jsonInString = mapper.writeValueAsString(driverData);
        doReturn(driverDO).when(driverService).create(any(DriverDO.class));

        driverController.createDriver(driverData);
        MvcResult result =
                mvc
                        .perform(
                                post("/v1/drivers")
                                        .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonInString))
                        .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertFalse(responseBody.contains("test"));

    }


    @Test
    public void testDeleteDriver() throws Exception
    {
        doNothing().when(driverService).delete(any(Long.class));
        driverController.deleteDriver(1L);
        MvcResult result =
                mvc
                        .perform(delete("/v1/drivers/{driverId}", 1))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}

