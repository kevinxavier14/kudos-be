package puter.balek.ksuSrikandi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AnggotaDTO  {

    private String noAnggota;

    private LocalDate tanggalBerlaku;

    private String agama;

    private String namaIbu;

    private String noTelpon;

    private String pekerjaan;

    private String statusMartial;

    private String alamat;

    private String namaAhliWaris;

    private String alamatAhliWaris;

    private String noTelponAhliWaris;
    private Long id;

    private String name;

    private String username;
    private String password;

    private String nik;

    private String role;

    private String tempatTanggalLahir;



}

