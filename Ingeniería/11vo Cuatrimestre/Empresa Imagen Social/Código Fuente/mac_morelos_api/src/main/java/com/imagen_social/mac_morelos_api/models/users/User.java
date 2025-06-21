package com.imagen_social.mac_morelos_api.models.users;

import jakarta.persistence.*;
import lombok.*;

import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.roles.Role;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", foreignKey = @ForeignKey(name = "fk_users_roles"))
    private Role role;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rfc", unique = true, length = 20)
    private String rfc;

    @Column(name = "curp", unique = true, length = 20)
    private String curp;

    @Column(name = "birthdate")
    private Timestamp birthdate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", foreignKey = @ForeignKey(name = "fk_users_addresses"))
    private Address address;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;

    @Column(name = "img_profile", length = 255)
    private String imgProfile;

    //Campo solo para ciudadanos
    @Column(name = "username", unique = true, length = 50)
    private String username;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "User: {" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", rfc='" + rfc + '\'' +
                ", curp='" + curp + '\'' +
                ", birthdate=" + birthdate +
                ", username='" + username + '\'' +
                ", status=" + status +
                '}';
    }
}