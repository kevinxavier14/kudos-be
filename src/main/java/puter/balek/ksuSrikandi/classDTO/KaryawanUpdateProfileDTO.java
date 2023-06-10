package puter.balek.ksuSrikandi.classDTO;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class KaryawanUpdateProfileDTO {

    private String password;
    private Long id;
    private String name;
    private String username;
    private String nik;
    private String role;
    private String tempatTanggalLahir;
    private String tempatLahir;
    private String tanggalLahir;
    private String passwordLama;
    private String passwordBaru;
    private String passwordBaruKonfirmasi;
    private String noPegawai;
}
