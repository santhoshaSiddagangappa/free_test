package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.EntityNotFoundException;

import java.util.List;

public interface CarService
{

    CarDO find(final Long carId) throws EntityNotFoundException;


    List<CarDO> findAllCars();


    CarDO create(final CarDO car) throws EntityNotFoundException;


    void update(final CarDO car) throws EntityNotFoundException;


    void delete(final Long carId) throws EntityNotFoundException;

}
