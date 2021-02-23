package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarMapper
{
    public static CarDO makeDriverDO(CarDTO carDTO)
    {
        return new CarDO(carDTO.getModel(), carDTO.getColor(), carDTO.getLicensePlate(),
                carDTO.getSeatCount(), carDTO.getConvertible(), carDTO.getRating(), carDTO.getEngineType(),
                carDTO.getTransmission(), carDTO.getCarType(), carDTO.getManufacturerId());
    }


    public static CarDTO makeDriverDTO(CarDO carDO)
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
                .setId(carDO.getId())
                .setColor(carDO.getColor())
                .setCarType(carDO.getCarType())
                .setModel(carDO.getModel())
                .setConvertible(carDO.getConvertible())
                .setRating(carDO.getRating())
                .setLicensePlate(carDO.getLicensePlate())
                .setSeatCount(carDO.getSeatCount())
                .setEngineType(carDO.getEngineType())
                .setTransmission(carDO.getTransmission())
                .setManufacturer(carDO.getManufacturer().getName());
        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeDriverDTOs(Collection<CarDO> cars)
    {
        return cars
            .stream()
            .map(CarMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }


    public static CarDTO convertParamsToDto(Map<String, String> searchParams)
    {
        CarDTO.CarDTOBuilder carDTOBuilder =
            CarDTO
                .newBuilder()
                .setRating(MapUtils.getFloat(searchParams, "rating"))
                .setConvertible(MapUtils.getBoolean(searchParams, "convertible"))
                .setLicensePlate(MapUtils.getString(searchParams, "licensePlate"))
                .setSeatCount(MapUtils.getInteger(searchParams, "seatCount"));

        return carDTOBuilder.createCarDTO();
    }
}
