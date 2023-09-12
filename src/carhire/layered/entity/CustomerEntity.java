package carhire.layered.entity;

import carhire.layered.dto.Embedded.Name;
import carhire.layered.entity.embeddeb.CustomerName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid")
    private int customerid;
    @Column(name = "nic")
    private String nic;
    @Embedded
    private CustomerName customerName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String mobile;
    @OneToMany(mappedBy = "customerEntity",targetEntity = HireEntity.class)
    private List<CustomerEntity> customerEntities;

    public CustomerEntity(int customerid, String nic, CustomerName customerName, String address, String mobile) {
        this.customerid = customerid;
        this.nic = nic;
        this.customerName = customerName;
        this.address = address;
        this.mobile = mobile;
    }
}
