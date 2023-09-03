package carhire.layered.view.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserTm {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String level;
    private String status;
}
