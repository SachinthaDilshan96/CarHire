package carhire.layered.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Hire")
public class HireEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hireId")
    private int hireId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VehicleID")
    private VehicleEntity vehicleEntity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CustomerID")
    private CustomerEntity customerEntity;
    @ManyToOne
    @JoinColumn(name = "OrderPlacedBy")
    private UserEntity userEntity;
    @Column(name = "From")
    private Date fromDate;
    @Column(name = "To")
    private Date toDate;
    @Column(name = "isReturned")
    private boolean isReturned;
    @Column(name = "total")
    private double total;
    @Column(name = "dailyRental")
    private double dailyRental;
    @Column(name = "deposit")
    private double deposit;
    @Column(name = "advance")
    private double advance;
    @Column(name = "balance")
    private double balance;
}
