package aero.models;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "email", unique = true)
    private String email;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;

    public User setRoleAndPassword(Roles role, String password) {
        this.setPassword(password);
        this.setRole(role);
        return this;
    }
}
