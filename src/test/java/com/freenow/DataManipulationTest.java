package com.freenow;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverCar;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;


@RunWith(MockitoJUnitRunner.class)
public abstract class DataManipulationTest
{

    public CarDO getCar()
    {
        CarDO car = new CarDO();
        car.setId(1L);
        car.setSeatCount(2);
        car.setRating(11.0F);
        car.setDateCreated(ZonedDateTime.now());
        car.setLicensePlate("A01");
        car.setEngineType(EngineType.DIESEL);
        car.setConvertible(true);
        
        ManufacturerDO manufacturer = new ManufacturerDO();
        manufacturer.setName("BMW");
        manufacturer.setId(1L);
        manufacturer.setDateCreated(ZonedDateTime.now());
        car.setManufacturer(manufacturer);
        return car;
    }


    public ManufacturerDO getManufacturer()
    {
        ManufacturerDO manufacturer = new ManufacturerDO();
        manufacturer.setDateCreated(ZonedDateTime.now());
        manufacturer.setId(1L);
        manufacturer.setName("BMW");
        return manufacturer;
    }


    public CarDTO getCarDTO()
    {
        return CarDTO
            .newBuilder()
            .setRating(4.0F)
            .setEngineType(EngineType.DIESEL)
            .setLicensePlate("A01")
            .setSeatCount(4)
            .setConvertible(true)
            .createCarDTO();
    }


    public DriverDO getDriver()
    {
        DriverDO driver = new DriverDO();
        driver.setId(1L);
        driver.setDateCreated(ZonedDateTime.now());
        driver.setDeleted(false);
        driver.setUsername("test");
        driver.setPassword("test");
        driver.setOnlineStatus(OnlineStatus.ONLINE);
        GeoCoordinate geoCoordinate = new GeoCoordinate(50, 60);
        driver.setCoordinate(geoCoordinate);
        return driver;
    }


    public DriverDTO getDriverDTO()
    {
        GeoCoordinate geoCoordinate = new GeoCoordinate(50, 60);
        return DriverDTO
            .newBuilder()
            .setId(1L)
            .setPassword("test")
            .setUsername("test")
            .setCoordinate(geoCoordinate)
            .createDriverDTO();
    }


    public DriverCar getDriverCar()
    {
        DriverCar driverCar = new DriverCar();
        driverCar.setCarId(1L);
        driverCar.setDriverId(1L);
        driverCar.setCarId(1L);
        return driverCar;
    }
}
