package carhire.layered.view.tm;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleTm {
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
    private Button button;
}
