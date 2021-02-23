package com.freenow.service.drivercar;


import com.freenow.dataaccessobject.DriverCarRepository;
import com.freenow.domainobject.DriverCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverCarServiceImpl implements DriverCarService
{

    @Autowired
    private DriverCarRepository driverCarRepository;


    @Override
    public void delete(DriverCar driverCar)
    {
        driverCarRepository.delete(driverCar);
    }


    @Override
    public DriverCar save(DriverCar driverCar)
    {
        return driverCarRepository.save(driverCar);
    }


    @Override
    public DriverCar findByDriverIdAndCarId(Long driverId, Long carId)
    {
        return driverCarRepository.findByDriverIdAndCarId(driverId, carId);
    }

}
