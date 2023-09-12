package carhire.layered.view.tm;


import lombok.*;

import javax.persistence.Table;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HireTm {
    private int hireId;
    private VehicleTm vehicle;
    private CustomerTm customer;
    private UserTm orderPlacedBy;
    private Date fromDate;
    private Date toDate;
    private String isReturned;
    private double total;
    private double dailyRental;
    private double deposit;
    private double advance;
    private double balance;
}
