package carhire.layered.entity.embeddeb;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class CustomerName {
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname ")
    private String lastName;
}
