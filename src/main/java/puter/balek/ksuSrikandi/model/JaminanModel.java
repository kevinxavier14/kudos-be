package puter.balek.ksuSrikandi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jaminan")
public class JaminanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nilaiJaminan")
    private Long nilaiJaminan;

    @Column(name = "fotoJaminan")
    private String fotoJaminan;

    @Column(name = "atasNama")
    private String atasNama;

    @Column(name = "diperolehDari")
    private String diperolehDari;

    @Column(name = "jenisJaminan")
    private String jenisJaminan;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "anggotaId", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnggotaModel anggota;


    @OneToOne(mappedBy = "jaminan")
    @JsonIgnore
    private PengajuanPinjamanModel pengajuanPinjaman;

    @OneToOne(mappedBy = "jaminan")
    @JsonIgnore
    private PinjamanModel pinjaman;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "pinjamanId")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private PinjamanModel pinjaman;

    //Jaminan Sertifikat
    @Column(name = "jenisKepemilikan")
    private String jenisKepemilikan;

    @Column(name = "lokasi")
    private String lokasi;

    @Column(name = "luas")
    private Long luas;

    // Jaminan Kendaraan
    @Column(name = "merktype")
    private String merkType;

    @Column(name = "tahun")
    private String tahun;

    @Column(name = "noPolisi")
    private String noPolisi;
}