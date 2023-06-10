package puter.balek.ksuSrikandi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "anggota")
public class AnggotaModel extends UserModel implements Serializable {

    @NotNull
    @Column(name = "noAnggota", nullable = false)
    private String noAnggota;

    @Column(name = "tanggalBerlaku")
    private LocalDate tanggalBerlaku;

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

    @NotNull
    @Column(name = "namaAhliWaris" , nullable = false)
    private String namaAhliWaris;


    @Column(name = "alamatAhliWaris")
    private String alamatAhliWaris;


    @Column(name = "noTelponAhliWaris")
    private String noTelponAhliWaris;

    @OneToMany(mappedBy = "anggota", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<JaminanModel> listJaminan;

    @OneToMany(mappedBy = "anggota", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PinjamanModel> listPinjaman;

    @OneToMany(mappedBy = "anggota", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PengajuanPinjamanModel> listPengajuanPinjaman;

    @OneToMany(mappedBy = "anggota", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PembayaranModel> listPembayaran;
}

