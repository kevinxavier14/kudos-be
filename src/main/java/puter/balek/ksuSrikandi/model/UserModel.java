package puter.balek.ksuSrikandi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

// This code is just for db testing purpose. Please re-code UserModel.java to the proper one.
@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "nik")
    private String nik;


    @Column(name = "role")
    private String role;

    @NotNull
    @Column(name = "tempatTanggalLahir")
    private String tempatTanggalLahir;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
}
