package com.freenow.domainobject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "driver_car")
@Getter
@Setter
public class DriverCar
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_id", unique = true)
    private Long driverId;

    @Column(name = "car_id", unique = true)
    private Long carId;

    public DriverCar(Long id, Long driverId, Long carId)
    {
        super();
        this.id = id;
        this.driverId = driverId;
        this.carId = carId;
    }


    public DriverCar()
    {
        super();
    }

}
