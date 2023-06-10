package puter.balek.ksuSrikandi.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PembayaranDTO {

    private String fotoBuktiPembayaran;
    private String jenisPembayaran;
    private String statusPembayaran;
    private String anggotaUsername;
    private Long pinjamanId;

    public PembayaranDTO() {

    }
}
