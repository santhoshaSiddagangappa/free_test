package com.freenow.service;

import com.freenow.DataManipulationTest;
import com.freenow.dataaccessobject.DriverCarRepository;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverCar;
import com.freenow.service.drivercar.DriverCarService;
import com.freenow.service.drivercar.DriverCarServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class DriverCarServiceTest extends DataManipulationTest
{

    @Mock
    private DriverCarRepository driverCarRepository;

    @InjectMocks
    private DriverCarServiceImpl driverCarService;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(DriverCarService.class);
    }


    @Test
    public void testDelete()
    {
        DriverCar driverCar = getDriverCar();
        doNothing().when(driverCarRepository).delete(any(DriverCar.class));
        driverCarService.delete(driverCar);
        verify(driverCarRepository, times(1)).delete(any(DriverCar.class));
    }


    @Test
    public void testSave()
    {
        DriverCar driverCar = getDriverCar();
        when(driverCarRepository.save(any(DriverCar.class))).thenReturn(driverCar);
        driverCarService.save(driverCar);
        verify(driverCarRepository, times(1)).save(any(DriverCar.class));
    }


    @Test
    public void testFindByDriverIdAndCarId()
    {
        DriverCar driverCar = getDriverCar();
        when(driverCarRepository.findByDriverIdAndCarId(any(Long.class), any(Long.class))).thenReturn(driverCar);
        driverCarService.findByDriverIdAndCarId(1L, 1L);
        verify(driverCarRepository, times(1)).findByDriverIdAndCarId(any(Long.class), any(Long.class));
    }


    @Test
    public void testFindDriverByCarAttributes()
    {
        CarDTO carData = getCarDTO();
        DriverDTO driverData = getDriverDTO();
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = getDriver();
        object[1] = getCar();
        objects.add(object);
        when(driverCarRepository.findDriverByFilterCriteria(any(CarDTO.class), any(DriverDTO.class))).thenReturn(objects);
        driverCarRepository.findDriverByFilterCriteria(carData, driverData);
        verify(driverCarRepository, times(1)).findDriverByFilterCriteria(any(CarDTO.class), any(DriverDTO.class));
    }
}

