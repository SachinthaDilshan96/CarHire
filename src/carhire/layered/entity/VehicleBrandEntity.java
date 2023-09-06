package carhire.layered.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "VehicleBrand")
public class VehicleBrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brandID")
    private int id;
    @Column(name = "brandName")
    private String vehicleBrand;
}
