package carhire.layered.dto;

import carhire.layered.dto.Embedded.Name;
import lombok.*;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDto {
    private int customerId;
    private String nic;
    private Name name;
    private String address;
    private int mobileNumber;

}
