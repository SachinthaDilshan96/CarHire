package carhire.layered.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleCategory")
public class VehicleCategoryEntity {
    @Id
    @Column(name = "VehicleCategoryId")
    private int id;
    @Column(name = "VehicleCategory")
    private String vehicleCategory;
}
