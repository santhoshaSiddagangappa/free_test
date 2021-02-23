package com.freenow.service.driver;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.controller.mapper.DriverMapper;
import com.freenow.dataaccessobject.DriverCarRepository;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverCar;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.DriverNotOnlineException;
import com.freenow.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.freenow.service.car.CarService;
import com.freenow.service.drivercar.DriverCarService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
@Log4j2
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    @Autowired
    private CarService carService;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverCarRepository driverCarRepository;

    @Autowired
    private DriverCarService driverCarService;

    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

    @Override
    public DriverDTO selectCarByDriver(Long driverId, Long carId) throws EntityNotFoundException, DriverNotOnlineException, CarAlreadyInUseException
    {
        Object[] object = new Object[2];
        DriverDO driver;
        CarDO car;
        try
        {
            driver = find(driverId);
            car = carService.find(carId);
            if (null != driver && null != car && OnlineStatus.ONLINE.equals(driver.getOnlineStatus()))
            {
                DriverCar driverCar = new DriverCar();
                driverCar.setDriverId(driver.getId());
                driverCar.setCarId(car.getId());
                driverCarService.save(driverCar);
                object[0] = driver;
                object[1] = car;
            }
            else if (null != driver && null != car && OnlineStatus.OFFLINE.equals(driver.getOnlineStatus()))
            {
                throw new DriverNotOnlineException("Driver is offline");
            }
        }
        catch (EntityNotFoundException e)
        {
            throw new EntityNotFoundException("Car or Driver entity not found ");
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CarAlreadyInUseException("Car is already taken by driver");
        }
        return DriverMapper.makeDriverDTO(object);
    }


    @Override
    public void deSelectCarByDriver(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException
    {
        DriverDO driver;
        CarDO car;
        try
        {
            driver = find(driverId);
            car = carService.find(carId);
            if (null != driver && null != car && OnlineStatus.ONLINE.equals(driver.getOnlineStatus()))
            {
                DriverCar driverCar = driverCarService.findByDriverIdAndCarId(driver.getId(), car.getId());
                driverCarService.delete(driverCar);
            }
        }
        catch (EntityNotFoundException e)
        {
            throw new EntityNotFoundException("Car or Driver entity not found ");
        }
        catch (InvalidDataAccessApiUsageException e)
        {
            throw new CarAlreadyInUseException("Car is already deselected by the driver");
        }
    }


    @Override
    public List<DriverDTO> findDriversByFilterCriteria(Map<String, String> params) throws EntityNotFoundException
    {
        List<DriverDTO> driverDataList = new ArrayList<>();
        try
        {
            CarDTO carFilter = CarMapper.convertParamsToDto(params);
            DriverDTO driverFilter = DriverMapper.convertParamsToDto(params);
            List<Object[]> drivers = driverCarRepository.findDriverByFilterCriteria(carFilter, driverFilter);

            drivers.forEach(object -> driverDataList.add(DriverMapper.makeDriverDTO(object)));
        }
        catch (Exception e)
        {
            throw new EntityNotFoundException("Driver entity not found ");
        }

        return driverDataList;
    }

}
