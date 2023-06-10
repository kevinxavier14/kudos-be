package puter.balek.ksuSrikandi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pembayaran")
public class PembayaranModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tanggalUpload")
    private LocalDate tanggalUpload;

    @Column(name = "jenisPembayaran")
    private String jenisPembayaran;

    @Column(name = "statusPembayaran")
    private String statusPembayaran;

    @Column(name = "fotoBuktiPembayaran")
    private String fotoBuktiPembayaran;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "anggotaId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnggotaModel anggota;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pinjamanId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PinjamanModel pinjaman;
}
