package com.freenow.service.drivercar;


import com.freenow.domainobject.DriverCar;

public interface DriverCarService
{

    void delete(DriverCar driverCar);


    DriverCar save(DriverCar driverCar);


    DriverCar findByDriverIdAndCarId(final Long driverId, final Long carId);

}
