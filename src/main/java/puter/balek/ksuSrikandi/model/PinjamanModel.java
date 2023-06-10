package puter.balek.ksuSrikandi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "pinjaman")
public class PinjamanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "tanngalCair")
    private LocalDate tanggalCair;


    @Column(name = "tanggalJatuhTempo")
    private LocalDate tanggalJatuhTempo;

    @Column(name = "tanggalSelesai")
    private LocalDate tanggalSelesai;

    @Column(name = "totalBulan")
    private Long totalBulan;

    //    Not sure about data type.
    @NotNull
    @Column(name = "sisaBulan")
    private Long sisaBulan;


    //    Not sure about data type.

    @Column(name = "sisaPinjaman")
    private Long sisaPinjaman;


    @Column(name = "nominalTerbayar")
    private Long nominalTerbayar;


    @Column(name = "nominalPinjaman")
    private Long nominalPinjaman;


    @Column(name = "statusPinjaman")
    private String statusPinjaman;

    //Berupa Nominal
    @Column(name = "bunga")
    private Double bunga;

    @Column(name = "jenisPinjaman")
    private String jenisPinjaman;

    //TagihanBulanan untuk pinjaman tahunan
    @Column(name = "tagihanBulanan")
    private Double tagihanBulanan;

    //TagihanBulanan untuk pinjaman musiman yang BUKAN bulan terakhir
    @Column(name = "tagihanMusimanBiasa")
    private Double tagihanMusimanBiasa;

    //TagihanBulanan untuk pinjaman musiman yang MERUPAKAN bulan terakhir
    @Column(name = "tagihanMusimanBulanTerakhir")
    private Double tagihanMusimanBulanTerakhir;

    //Untuk bukti pencairan pinjaman
    @Column(name = "buktiPencairanPinjaman")
    private String buktiPencairanPinjaman;


    //Untuk konfirmasi pencairan pinjaman dari anggota
    @Column(name = "isPinjamanConfirmedByAnggota")
    private Boolean isPinjamanConfirmedByAnggota;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "anggotaId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnggotaModel anggota;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jaminanId", referencedColumnName = "id")
    private JaminanModel jaminan;

//    @OneToMany(mappedBy = "pinjaman", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<JaminanModel> listJaminan;

    @OneToMany(mappedBy = "pinjaman", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PembayaranModel> listPembayaran;
}

