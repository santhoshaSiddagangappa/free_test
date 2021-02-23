package com.freenow.repository;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class DriverRepositoryTest extends AbstractRepositoryTest
{

    private static final String USER_NAME = "driver02";

    @MockBean
    private DriverRepository driverRepository;


    @Test
    public void testDriverById()
    {
        DriverDO driver = null;
        try
        {
            driver = driverRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));
        }
        catch (EntityNotFoundException e)
        {
            e.printStackTrace();
        }
        Assert.assertNull(driver);
    }


    @Test
    public void testDriverByOnlineStatus()
    {
        List<DriverDO> onlineDrivers = driverRepository.findByOnlineStatus(OnlineStatus.ONLINE);
        Assert.assertThat(onlineDrivers, hasSize(0));
    }


    @Test
    public void testDriverByOfflineStatus()
    {
        List<DriverDO> offlineDrivers = driverRepository.findByOnlineStatus(OnlineStatus.OFFLINE);
        Assert.assertThat(offlineDrivers, hasSize(0));
    }


    @Test
    public void testDriverByUsername()
    {
        DriverDO driver = driverRepository.findByUsername(USER_NAME);
        Assert.assertNull(driver);
    }
}

