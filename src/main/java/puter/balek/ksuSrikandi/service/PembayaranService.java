package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.DTO.PembayaranDTO;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.PembayaranModel;
import puter.balek.ksuSrikandi.model.PinjamanModel;
import puter.balek.ksuSrikandi.model.UserModel;
import puter.balek.ksuSrikandi.repository.PembayaranRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PembayaranService {
    @Autowired
    private PembayaranRepository pembayaranRepository;

    @Autowired
    private AnggotaService anggotaService;

    @Autowired
    private PinjamanService pinjamanService;

    public PembayaranModel savePembayaran(PembayaranDTO pembayaranDTO){

        PembayaranModel pembayaran = new PembayaranModel();
        LocalDate tanggalUpload = LocalDate.now();

        pembayaran.setTanggalUpload(tanggalUpload);
        pembayaran.setJenisPembayaran(pembayaranDTO.getJenisPembayaran());
        pembayaran.setStatusPembayaran(pembayaranDTO.getStatusPembayaran());
        pembayaran.setFotoBuktiPembayaran(pembayaranDTO.getFotoBuktiPembayaran());
        pembayaran.setAnggota(anggotaService.getAnggotaByUsername(pembayaranDTO.getAnggotaUsername()));
        pembayaran.setPinjaman(pinjamanService.getPinjamanById(pembayaranDTO.getPinjamanId()));

        return pembayaranRepository.save(pembayaran);
    }

    public PembayaranModel updateStatusPembayaran(String status, Long id) {
        PembayaranModel pembayaran = getPembayaranById(id);
        PinjamanModel pinjaman = pembayaran.getPinjaman();

        pembayaran.setStatusPembayaran(status);
        String statusPembayaran = pembayaran.getStatusPembayaran();

        if (statusPembayaran.equals("diterima")) {
            LocalDate tanggalJatuhTempo = pinjaman.getTanggalJatuhTempo();
            LocalDate tanggalJatuhTempoOneMonthLater = tanggalJatuhTempo.plusMonths(1);
            pinjaman.setTanggalJatuhTempo(tanggalJatuhTempoOneMonthLater);

            if (pinjaman.getTagihanBulanan() == null) {
                pinjaman.setNominalTerbayar((long) (pinjaman.getNominalTerbayar() + pinjaman.getTagihanMusimanBiasa()));
                pinjaman.setSisaPinjaman(pinjaman.getSisaPinjaman() - pinjaman.getNominalTerbayar());
            } else {
                pinjaman.setNominalTerbayar((long) (pinjaman.getNominalTerbayar() + pinjaman.getTagihanBulanan()));
                pinjaman.setSisaPinjaman(pinjaman.getSisaPinjaman() - pinjaman.getNominalTerbayar());
            }
        }

        return pembayaranRepository.save(pembayaran);
    }

    public PembayaranModel getPembayaranById(Long id){
        return pembayaranRepository.findById(id).orElse(null);
    }

    public void deletePembayaranById(Long id){
        pembayaranRepository.deleteById(id);
    }

    public List<PembayaranModel> getAllPembayaran() { return pembayaranRepository.findAll(); }

    public List<PembayaranModel> getAllPembayaranByPinjamanId(Long id) { return pembayaranRepository.findAllByPinjamanId(id); }
}
