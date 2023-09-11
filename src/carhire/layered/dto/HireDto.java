package carhire.layered.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HireDto {
    private int hireId;
    private int vehicleId;
    private int customerId;
    private int orderPlacedBy;
    private Date fromDate;
    private Date toDate;
    private boolean isReturned;
    private double total;
    private double dailyRental;
    private double deposit;
    private double advance;
    private double balance;
}
