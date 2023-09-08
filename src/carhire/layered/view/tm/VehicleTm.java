package carhire.layered.view.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleTm {
    private int vehicleId;
    private String vehicleNumber;
    private int brandId;
    private int year;
    private String model;
    private int vehicleTypeId;
    private String transmission;
    private int noOfSeats;
    private double dailyRental;
    private String status;
}
