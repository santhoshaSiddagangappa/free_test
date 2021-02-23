package com.freenow.repository;

import com.freenow.dataaccessobject.DriverCarRepository;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverCar;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public class DriverCarRepositoryTest extends AbstractRepositoryTest
{

    @MockBean
    private DriverCarRepository driverCarRepository;


    @Test
    public void testFindByDriverIdAndCarId()
    {
        DriverCar driverCar = getDriverCar();
        driverCarRepository.save(driverCar);
        DriverCar savedDriverCar = driverCarRepository.findByDriverIdAndCarId(1L, 1L);
        Assert.assertNull(savedDriverCar);
    }


    @Test
    public void testFindByDriverSeatCount()
    {
        DriverCar driverCar = getDriverCar();
        driverCarRepository.save(driverCar);
        CarDTO carData = CarDTO.newBuilder().setSeatCount(3).createCarDTO();
        DriverDTO driverData = DriverDTO.newBuilder().setUsername("test").createDriverDTO();
        List<Object[]> drivers = driverCarRepository.findDriverByFilterCriteria(carData, driverData);
        drivers.forEach(obj -> {
            DriverDO driver = (DriverDO) obj[0];
            CarDO car = (CarDO) obj[1];
            Assert.assertEquals(OnlineStatus.OFFLINE, driver.getOnlineStatus());
            Assert.assertEquals(Integer.valueOf(3), car.getSeatCount());
        });
    }


    @Test
    public void testAlreadyExistCarWithDriver()
    {
        DriverCar driverCar1 = getDriverCar();
        DriverCar driverCar2 = getDriverCar();
        driverCarRepository.save(driverCar1);
        DriverCar savedDriverCar = driverCarRepository.findByDriverIdAndCarId(1L, 1L);
        driverCarRepository.save(driverCar2);
        Assert.assertNull(savedDriverCar);
    }


    private DriverCar getDriverCar()
    {
        DriverCar driverCar = new DriverCar();
        driverCar.setDriverId(1L);
        driverCar.setCarId(1L);
        return driverCar;
    }

}

