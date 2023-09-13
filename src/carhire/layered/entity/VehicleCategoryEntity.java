package carhire.layered.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "vehiclecategory")
public class VehicleCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private int categoryID;
    @Column(name = "Category")
    private String vehicleCategory;
    @OneToMany(mappedBy = "vehicleCategoryEntity",targetEntity = VehicleEntity.class)
    private List<VehicleEntity> vehicleEntities;

    public VehicleCategoryEntity(int categoryID, String vehicleCategory) {
        this.categoryID = categoryID;
        this.vehicleCategory = vehicleCategory;
    }
}
