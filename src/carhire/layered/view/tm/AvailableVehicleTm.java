package carhire.layered.view.tm;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AvailableVehicleTm {
    private int vehicleId;
    private String vehicleNumber;
    private VehicleBrandTm vehicleBrandTm;
    private int year;
    private String model;
    private VehicleCategoryTm vehicleCategoryTm;
    private String transmission;
    private int noOfSeats;
    private double dailyRental;
    private String status;

}
