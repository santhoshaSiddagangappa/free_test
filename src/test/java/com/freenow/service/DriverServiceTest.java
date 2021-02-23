package com.freenow.service;

import com.freenow.DataManipulationTest;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.service.driver.DefaultDriverService;
import com.freenow.service.driver.DriverService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DriverServiceTest extends DataManipulationTest
{

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DefaultDriverService driverService;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(DriverService.class);
    }


    @Test
    public void testCreate() throws ConstraintsViolationException
    {
        DriverDO driver = getDriver();
        when(driverRepository.save(any(DriverDO.class))).thenReturn(driver);
        driverService.create(driver);
        verify(driverRepository, times(1)).save(any(DriverDO.class));
    }


    @Test
    public void testFindByOnlineStatus()
    {
        List<DriverDO> drivers = Collections.singletonList(getDriver());
        when(driverRepository.findByOnlineStatus(any(OnlineStatus.class))).thenReturn(drivers);
        driverRepository.findByOnlineStatus(OnlineStatus.ONLINE);
        verify(driverRepository, times(1)).findByOnlineStatus(any(OnlineStatus.class));
    }

}
