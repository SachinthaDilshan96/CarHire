package carhire.layered.view.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTm {
    private int customerId;
    private String customerNic;
    private String firstName;
    private String lastName;
    private String address;
    private String mobile;
}
