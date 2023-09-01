package carhire.layered.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String level;
}
