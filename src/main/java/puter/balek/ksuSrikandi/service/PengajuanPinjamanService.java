package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.PengajuanPinjamanModel;
import puter.balek.ksuSrikandi.model.UserModel;
import puter.balek.ksuSrikandi.repository.PengajuanPinjamanRepository;
import puter.balek.ksuSrikandi.restmodel.PengajuanPinjamanDTO;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PengajuanPinjamanService {

    @Autowired
    private PengajuanPinjamanRepository pengajuanPinjamanRepository;


    public PengajuanPinjamanModel getPengajuanPinjamanById(Long id) {
        return pengajuanPinjamanRepository.findById(id).orElse(null);
    };

    public PengajuanPinjamanModel savePengajuanPinjaman(PengajuanPinjamanModel pengajuanPinjaman) {
        pengajuanPinjaman.setTanggalPengajuan(LocalDateTime.now());
        pengajuanPinjaman.setNominalTerima(pengajuanPinjaman.getNominalPengajuan());
        return pengajuanPinjamanRepository.save(pengajuanPinjaman);
    }

    public List<PengajuanPinjamanModel> getAllPengajuanPinjaman() {
        return pengajuanPinjamanRepository.findAll();
    }

    public PengajuanPinjamanModel updateHasilSurveyPengajuanPinjaman(PengajuanPinjamanModel pengajuanPinjaman, String hasilSurvey) {
        pengajuanPinjaman.setHasilSurvey(hasilSurvey);
        return pengajuanPinjamanRepository.save(pengajuanPinjaman);
    }

    public PengajuanPinjamanModel updateStatusPengajuanPinjaman(PengajuanPinjamanModel pengajuanPinjaman, String status) {
        pengajuanPinjaman.setStatus(status);
        return  pengajuanPinjamanRepository.save(pengajuanPinjaman);
    }

    public List<PengajuanPinjamanModel> getAllPengajuanPinjamanAnggota(Long id) {
        return pengajuanPinjamanRepository.findAllByAnggotaId(id);
    }

    public PengajuanPinjamanModel updatePengajuanPinjaman(Long id, PengajuanPinjamanModel pengajuanPinjamanUpdate) {
        PengajuanPinjamanModel pengajuanPinjaman = getPengajuanPinjamanById(id);
        pengajuanPinjaman.setStatus(pengajuanPinjamanUpdate.getStatus());
        pengajuanPinjaman.setHasilSurvey(pengajuanPinjamanUpdate.getHasilSurvey());
        return pengajuanPinjamanRepository.save(pengajuanPinjaman);
    }

    public  PengajuanPinjamanModel terimaPengajuanPinjaman(Long id) {
        PengajuanPinjamanModel pengajuanPinjaman = getPengajuanPinjamanById(id);
        pengajuanPinjaman.setStatus("diterima");
        return pengajuanPinjamanRepository.save(pengajuanPinjaman);
    }

    public  PengajuanPinjamanModel tolakPengajuanPinjaman(Long id) {
        PengajuanPinjamanModel pengajuanPinjaman = getPengajuanPinjamanById(id);
        pengajuanPinjaman.setStatus("ditolak");
        return pengajuanPinjamanRepository.save(pengajuanPinjaman);
    }

    public  PengajuanPinjamanModel surveyPengajuanPinjaman(Long id) {
        PengajuanPinjamanModel pengajuanPinjaman = getPengajuanPinjamanById(id);
        pengajuanPinjaman.setStatus("dalam tahap survey");
        return pengajuanPinjamanRepository.save(pengajuanPinjaman);
    }
    
    public List<PengajuanPinjamanModel> getAllPengajuanPinjamanAngggota(AnggotaModel anggota) {
        return anggota.getListPengajuanPinjaman();
    }

    // method update dari boy
    // public PengajuanPinjamanModel updatePengajuanPinjaman(PengajuanPinjamanModel pengajuanPinjaman, String status, String hasilSurvey) {
    //     pengajuanPinjaman.setStatus(status);
    //     pengajuanPinjaman.setHasilSurvey(hasilSurvey);
    //     return pengajuanPinjaman;
    // }

    public PengajuanPinjamanDTO setDTO(PengajuanPinjamanModel pengajuanPinjamanModel) {
        PengajuanPinjamanDTO pengajuan = new PengajuanPinjamanDTO();

        pengajuan.setId(pengajuanPinjamanModel.getId());
        pengajuan.setName(pengajuanPinjamanModel.getAnggota().getName());
        pengajuan.setTanggalPengajuan(pengajuanPinjamanModel.getTanggalPengajuan().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        pengajuan.setStatus(pengajuanPinjamanModel.getStatus());

        BigDecimal currencyFormat = BigDecimal.valueOf(pengajuanPinjamanModel.getNominalPengajuan());
        pengajuan.setNominalPengajuan(String.format("Rp" + "%,.2f", currencyFormat));

        return pengajuan;
    }
}
