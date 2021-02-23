package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/cars")
public class CarController
{
    private final CarService carService;

    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping(value = "/{carId}")
    public ResponseEntity<CarDTO> getCar(@PathVariable long carId) throws EntityNotFoundException
    {
        return new ResponseEntity<>(CarMapper.makeDriverDTO(carService.find(carId)), HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CarDTO>> getAllCars()
    {
        return new ResponseEntity<>(CarMapper.makeDriverDTOs(carService.findAllCars()), HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO carData) throws EntityNotFoundException
    {
        CarDO carDO = CarMapper.makeDriverDO(carData);
        return new ResponseEntity<>(CarMapper.makeDriverDTO(carService.create(carDO)), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<Void> updateCar(@Valid @RequestBody CarDTO carData) throws EntityNotFoundException
    {
        CarDO carDO = CarMapper.makeDriverDO(carData);
        carService.update(carDO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(value = "/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

