package com.freenow.service;

import com.freenow.DataManipulationTest;
import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.ManufacturerRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.service.car.CarServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CarServiceTest extends DataManipulationTest
{

    @Mock
    private CarRepository carRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private CarServiceImpl carService;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(CarService.class);
    }


    @Test
    public void testFindAllCars()
    {
        Iterable<CarDO> cars = Collections.singletonList(getCar());
        when(carRepository.findAll()).thenReturn(cars);
        carService.findAllCars();
        verify(carRepository, times(1)).findAll();
    }


    @Test
    public void testCreate() throws EntityNotFoundException
    {
        CarDO car = getCar();
        ManufacturerDO manufacturer = getManufacturer();
        when(manufacturerRepository.findByName(any(String.class))).thenReturn(manufacturer);
        when(carRepository.save(any(CarDO.class))).thenReturn(car);
        carService.create(car);
        verify(manufacturerRepository, times(1)).findByName(any(String.class));
        verify(carRepository, times(1)).save(car);
    }

}

