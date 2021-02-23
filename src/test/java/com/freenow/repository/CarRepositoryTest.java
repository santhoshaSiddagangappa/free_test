package com.freenow.repository;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.EntityNotFoundException;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class CarRepositoryTest extends AbstractRepositoryTest
{

    @MockBean
    private CarRepository carRepository;


    @Test
    public void testDriverById()
    {
        CarDO car = null;
        try
        {
            car = carRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));
        }
        catch (EntityNotFoundException e)
        {
            e.printStackTrace();
        }
        Assert.assertNull(car);
    }


    @Test
    public void testAllCars()
    {
        List<CarDO> cars = Lists.newArrayList(carRepository.findAll());
        Assert.assertThat(cars, hasSize(0));
    }

}

