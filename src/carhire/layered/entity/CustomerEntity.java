package carhire.layered.entity;

import carhire.layered.dto.Embedded.Name;
import carhire.layered.entity.embeddeb.CustomerName;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nic")
    private String nic;
    private CustomerName customerName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private int mobile;
}
