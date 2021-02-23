package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        return driverDTOBuilder.createDriverDTO();
    }

    public static DriverDTO makeDriverDTO(Object[] object)
    {
        DriverDO driver = (DriverDO) object[0];
        CarDO car = (CarDO) object[1];
        CarDTO.CarDTOBuilder carDTOBuilder =
                CarDTO
                        .newBuilder()
                        .setId(car.getId())
                        .setColor(car.getColor())
                        .setCarType(car.getCarType())
                        .setModel(car.getModel())
                        .setConvertible(car.getConvertible())
                        .setRating(car.getRating())
                        .setLicensePlate(car.getLicensePlate())
                        .setSeatCount(car.getSeatCount())
                        .setEngineType(car.getEngineType())
                        .setTransmission(car.getTransmission())
                        .setManufacturer(car.getManufacturer().getName());

        DriverDTO carDriverDTO = makeDriverDTO(driver);
        carDriverDTO.setCarData(carDTOBuilder.createCarDTO());
        return carDriverDTO;
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }

    public static DriverDTO convertParamsToDto(Map<String, String> searchParams)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder =
                DriverDTO
                        .newBuilder()
                        .setUsername(MapUtils.getString(searchParams, "username"))
                        .setStatus(MapUtils.getString(searchParams, "status"));

        return driverDTOBuilder.createDriverDTO();
    }
}
