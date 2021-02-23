package com.freenow.dataaccessobject;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverCar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverCarRepository extends CrudRepository<DriverCar, Long>
{
    DriverCar findByDriverIdAndCarId(final Long driverId, final Long carId);


    @Query("SELECT D, C FROM CarDO C, DriverDO D, DriverCar DC "
        +
        "WHERE DC.carId = C.id AND DC.driverId = D.id " +
        "AND (C.rating = :#{#carData.rating} OR C.licensePlate = :#{#carData.licensePlate} " +
        "OR C.seatCount = :#{#carData.seatCount} OR C.convertible = :#{#carData.convertible} " +
        "OR C.engineType = :#{#carData.engineType} OR D.username = :#{#driverData.username})")
    List<Object[]> findDriverByFilterCriteria(@Param("carData") final CarDTO carData, @Param("driverData") final DriverDTO driverData);

}
