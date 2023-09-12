package carhire.layered.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "email")
    private String email;

    public UserEntity(int userId, String firstName, String lastName, String email, String password, String level, String status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.level = level;
        this.status = status;
    }

    @Column(name = "password")
    private String password;
    @Column(name = "level")
    private String level;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "userEntity",targetEntity = HireEntity.class)
    private List<UserEntity> userEntities;

}
