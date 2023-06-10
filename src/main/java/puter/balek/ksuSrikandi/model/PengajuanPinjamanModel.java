package puter.balek.ksuSrikandi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pengajuanPinjaman")
public class PengajuanPinjamanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "nominalPengajuan")
    private Long nominalPengajuan;


    @Column(name = "nominalTerima")
    private Long nominalTerima;


    @Column(name = "status")
    private String status;

    @Column(name = "hasilSurvey")
    private String hasilSurvey;

    @Column(name = "jangkaWaktu")
    private Long jangkaWaktu;

    @Column(name = "tanggalPengajuan")
    private LocalDateTime tanggalPengajuan;

    @Column(name = "jenisPinjaman")
    private String jenisPinjaman;

    @Column(name = "caraPembayaran")
    private String caraPembayaran;

    @Column(name = "bunga")
    private Float bunga;

    @Column(name = "penggunaanPinjaman")
    private String penggunaanPinjaman;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "anggotaId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnggotaModel anggota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "surveyorId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SurveyorModel surveyor;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jaminan_id", referencedColumnName = "id")
    private JaminanModel jaminan;
}
