package carhire.layered.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Vehicle")
@AllArgsConstructor
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
    @Column(name = "Status")
    private String status;
}
