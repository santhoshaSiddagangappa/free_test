package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.ManufacturerRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService
{

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;


    @Override
    public CarDO find(final Long carId) throws EntityNotFoundException
    {
        return carCheck(carId);
    }


    @Override
    public List<CarDO> findAllCars()
    {
        List<CarDO> target = new ArrayList<>();
        Iterable<CarDO> iterable = carRepository.findAll();
        iterable.forEach(target::add);
        return target;
    }


    @Override
    public CarDO create(final CarDO car) throws EntityNotFoundException
    {
        car.setManufacturer(manufacturerCheck(car));
        return carRepository.save(car);
    }


    @Override
    @Transactional
    public void update(final CarDO car) throws EntityNotFoundException
    {
        CarDO updateCar = carCheck(car.getId());
        updateCar.setManufacturer(manufacturerCheck(car));
        updateCar.setConvertible(car.getConvertible());
        updateCar.setEngineType(car.getEngineType());
        updateCar.setLicensePlate(car.getLicensePlate());
        updateCar.setRating(car.getRating());
        updateCar.setSeatCount(car.getSeatCount());
    }


    @Override
    @Transactional
    public void delete(final Long carId) throws EntityNotFoundException
    {
        CarDO car = carCheck(carId);
        car.setDeleted(Boolean.TRUE);
    }


    private CarDO carCheck(final Long carId) throws EntityNotFoundException
    {
        return carRepository
                .findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
    }


    private ManufacturerDO manufacturerCheck(final CarDO car) throws EntityNotFoundException
    {
        String manufacturerName = car.getManufacturer().getName();
        ManufacturerDO manufacturer = manufacturerRepository.findByName(manufacturerName);
        if (null == manufacturer)
        {
            throw new EntityNotFoundException("Manufacturer not found with this name: " + manufacturerName);
        }
        return manufacturer;
    }
}

