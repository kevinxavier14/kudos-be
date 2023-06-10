package puter.balek.ksuSrikandi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pengajuanAnggota")
public class PengajuanAnggotaModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "nik", nullable = false)
    private String nik;


    @Column(name = "tanggalPengajuan")
    private LocalDate tanggalPengajuan;

    @Column(name = "Agama")
    private String agama;


    @Column(name = "namaIbu")
    private String namaIbu;


    @Column(name = "noTelpon")
    private String noTelpon;

    @Column(name = "pekerjaan")
    private String pekerjaan;


    @Column(name = "statusMartial")
    private String statusMartial;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "namaAhliWaris")
    private String namaAhliWaris;


    @Column(name = "alamatAhliWaris")
    private String alamatAhliWaris;
    
    @Column(name = "alasanPenolakan", nullable = true)
    private String alasanPenolakan;

    @NotNull
    @Column(name = "noTelponAhliWaris")
    private String noTelponAhliWaris;

    @NotNull
    @Column(name = "fotoKtp")
    private String fotoKtp;

    @Column(name = "simpananPokok")
    private String fotsimpananPokokoKtp;

    @Column(name = "statusPengajuan")
    private String statusPengajuan;

    @NotNull
    @Column(name = "tempatTanggalLahir")
    private String tempatTanggalLahir;


}

