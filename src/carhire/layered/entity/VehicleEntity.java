package carhire.layered.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicle")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VehicleID")
    private int vehicleId;
    @Column(name = "VehicleNumber")
    private String vehicleNumber;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Brand")
    private VehicleBrandEntity vehicleBrandEntity;
    @Column(name = "Year")
    private int year;
    @Column(name = "Model")
    private String model;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VehicleType")
    private VehicleCategoryEntity vehicleCategoryEntity;
    @Column(name = "Transmission")
    private String transmission;
    @Column(name = "NoOfSeats")
    private int noOfSeats;
    @Column(name = "DailyRental")
    private double dailyRental;

    public VehicleEntity(int vehicleId, String vehicleNumber, VehicleBrandEntity vehicleBrandEntity, int year, String model, VehicleCategoryEntity vehicleCategoryEntity, String transmission, int noOfSeats, double dailyRental, String status) {
        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleBrandEntity = vehicleBrandEntity;
        this.year = year;
        this.model = model;
        this.vehicleCategoryEntity = vehicleCategoryEntity;
        this.transmission = transmission;
        this.noOfSeats = noOfSeats;
        this.dailyRental = dailyRental;
        this.status = status;
    }

    @Column(name = "Status")
    private String status;
    @OneToMany(mappedBy = "vehicleEntity",targetEntity = HireEntity.class)
    private List<HireEntity> hireEntities;

}
