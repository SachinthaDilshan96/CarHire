package carhire.layered.dto;

import lombok.*;

import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleDto {
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
