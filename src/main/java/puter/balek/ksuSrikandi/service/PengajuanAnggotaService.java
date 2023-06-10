package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import puter.balek.ksuSrikandi.DTO.PengajuanAnggotaDTO;
import puter.balek.ksuSrikandi.DTO.UpdateStatusDTO;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.PengajuanAnggotaModel;
import puter.balek.ksuSrikandi.repository.AnggotaRepository;
import puter.balek.ksuSrikandi.repository.PengajuanAnggotaRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PengajuanAnggotaService {

    @Autowired
    private PengajuanAnggotaRepository pengajuanAnggotaRepository;
    
    @Autowired
    private AnggotaRepository anggotaRepository;

    @Autowired
    private UserService userService;

    public PengajuanAnggotaModel getPengajuanAnggotaById(Long id) {
        return pengajuanAnggotaRepository.findById(id).orElse(null);
    };

    public PengajuanAnggotaModel savePengajuanAnggota(PengajuanAnggotaDTO pengajuanAnggotaDTO) {
        
        PengajuanAnggotaModel pengajuanAnggotaModel = new PengajuanAnggotaModel();

        pengajuanAnggotaModel.setUsername(pengajuanAnggotaDTO.getUsername());
        pengajuanAnggotaModel.setNama(pengajuanAnggotaDTO.getNama());
        pengajuanAnggotaModel.setNik(pengajuanAnggotaDTO.getNik());
        pengajuanAnggotaModel.setTanggalPengajuan(LocalDate.now());
        pengajuanAnggotaModel.setTempatTanggalLahir(pengajuanAnggotaDTO.getTempatLahir() + ", " +pengajuanAnggotaDTO.getTanggalLahir());
        pengajuanAnggotaModel.setAgama(pengajuanAnggotaDTO.getAgama());
        pengajuanAnggotaModel.setNamaIbu(pengajuanAnggotaDTO.getNamaIbu());
        pengajuanAnggotaModel.setNoTelpon(pengajuanAnggotaDTO.getNoTelpon());
        pengajuanAnggotaModel.setPekerjaan(pengajuanAnggotaDTO.getPekerjaan());
        pengajuanAnggotaModel.setStatusMartial(pengajuanAnggotaDTO.getStatusMartial());
        pengajuanAnggotaModel.setAlamat(pengajuanAnggotaDTO.getAlamat());
        pengajuanAnggotaModel.setNamaAhliWaris(pengajuanAnggotaDTO.getNamaAhliWaris());
        pengajuanAnggotaModel.setAlamatAhliWaris(pengajuanAnggotaDTO.getAlamatAhliWaris());
        pengajuanAnggotaModel.setNoTelponAhliWaris(pengajuanAnggotaDTO.getNoTelponAhliWaris());
        pengajuanAnggotaModel.setFotoKtp(pengajuanAnggotaDTO.getFotoKtp());
        pengajuanAnggotaModel.setFotsimpananPokokoKtp(pengajuanAnggotaDTO.getFotsimpananPokokoKtp());
        pengajuanAnggotaModel.setStatusPengajuan(pengajuanAnggotaDTO.getStatusPengajuan());

        return pengajuanAnggotaRepository.save(pengajuanAnggotaModel);
    }

    public PengajuanAnggotaModel updateStatus(UpdateStatusDTO updateStatusDTO, Long id) {
        
        PengajuanAnggotaModel pengajuanAnggota = this.getPengajuanAnggotaById(id);
        pengajuanAnggota.setStatusPengajuan(updateStatusDTO.getStatus());
        pengajuanAnggota.setAlasanPenolakan(updateStatusDTO.getMessage());

        return pengajuanAnggotaRepository.save(pengajuanAnggota);
    }

    public AnggotaModel createAnggota(PengajuanAnggotaModel pengajuanAnggota) {
        AnggotaModel anggotaNew = new AnggotaModel();
        anggotaNew.setUsername(pengajuanAnggota.getUsername());
        anggotaNew.setName(pengajuanAnggota.getNama());
        anggotaNew.setNik(pengajuanAnggota.getNik());
        anggotaNew.setRole("ANGGOTA");
        anggotaNew.setNoAnggota("arsan 1234");
        anggotaNew.setAgama(pengajuanAnggota.getAgama());
        anggotaNew.setTempatTanggalLahir(pengajuanAnggota.getTempatTanggalLahir());
        anggotaNew.setPassword(userService.encrypt(pengajuanAnggota.getUsername()));
        anggotaNew.setNamaIbu(pengajuanAnggota.getNamaIbu());
        anggotaNew.setNoTelpon(pengajuanAnggota.getNoTelpon());
        anggotaNew.setPekerjaan(pengajuanAnggota.getPekerjaan());
        anggotaNew.setStatusMartial(pengajuanAnggota.getStatusMartial());
        anggotaNew.setAlamat(pengajuanAnggota.getAlamat());
        anggotaNew.setNamaAhliWaris(pengajuanAnggota.getNamaAhliWaris());
        anggotaNew.setAlamatAhliWaris(pengajuanAnggota.getAlamatAhliWaris());
        anggotaNew.setNoTelponAhliWaris(pengajuanAnggota.getNoTelponAhliWaris());

        // anggotaNew.setFotoKtp(pengajuanAnggota.getFotoKtp());
        // anggotaNew.setFotsimpananPokokoKtp(pengajuanAnggota.getFotsimpananPokokoKtp());
        // anggotaNew.setStatusPengajuan(pengajuanAnggota.getStatusPengajuan());

        anggotaRepository.save(anggotaNew);
        return anggotaNew;
    }


    public List<PengajuanAnggotaModel> getAllPengajuanAnggota() {
        return pengajuanAnggotaRepository.findAll();
    }

    // public PengajuanAnggotaModel updatePengajuanAnggota(PengajuanAnggotaModel pengajuanAnggota, String status, String hasilSurvey) {
    //     pengajuanAnggota.setStatus(status);
    //     pengajuanAnggota.setHasilSurvey(hasilSurvey);
    //     return pengajuanAnggota;
    // }

    


}
