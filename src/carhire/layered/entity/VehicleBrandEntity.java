package carhire.layered.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "VehicleBrand")
public class VehicleBrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brandId")
    private int id;
    @Column(name = "brandName")
    private String vehicleBrand;
    @OneToMany(mappedBy = "vehicleBrandEntity",targetEntity = VehicleEntity.class)
    private List<VehicleEntity> vehicleEntities;

    public VehicleBrandEntity(int id, String vehicleBrand) {
        this.id = id;
        this.vehicleBrand = vehicleBrand;
    }
}
