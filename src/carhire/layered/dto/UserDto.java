package carhire.layered.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
}
