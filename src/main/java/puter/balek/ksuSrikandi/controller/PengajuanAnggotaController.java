package puter.balek.ksuSrikandi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import puter.balek.ksuSrikandi.DTO.PengajuanAnggotaDTO;
import puter.balek.ksuSrikandi.DTO.UpdateStatusDTO;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.PengajuanAnggotaModel;
import puter.balek.ksuSrikandi.repository.PengajuanAnggotaRepository;
import puter.balek.ksuSrikandi.service.PengajuanAnggotaService;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@RestController
@RequestMapping("/api/v1/pengajuan-anggota")
@CrossOrigin(origins = "http://localhost:3000")
// @RequestMapping("/api/v1/pengajuan-anggota")
public class PengajuanAnggotaController {

    private final String ACCOUNT_SID = "ACe49475696df91c9c6f127166bdbd6a17";
    private final String AUTH_TOKEN = "80569ca7a2b46d0b15720c3fbe3a7323";

    @Autowired
    private PengajuanAnggotaService pengajuanAnggotaService;
    
    @Autowired
    private PengajuanAnggotaRepository pengajuanAnggotaRepository;

    @GetMapping("/{id}")
    public ResponseEntity<PengajuanAnggotaModel> getPengajuanAnggotaById(@PathVariable("id") Long id) {
        PengajuanAnggotaModel pengajuanAnggota = pengajuanAnggotaService.getPengajuanAnggotaById(id);
        if (pengajuanAnggota == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pengajuanAnggota, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PengajuanAnggotaModel>> getAllPengajuanAnggota() {
        List<PengajuanAnggotaModel> listPengajuanAnggota = pengajuanAnggotaService.getAllPengajuanAnggota();
        if (listPengajuanAnggota.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listPengajuanAnggota, HttpStatus.OK);
    }



    @PostMapping("/add")
    public ResponseEntity<PengajuanAnggotaModel> savePengajuanAnggota(@RequestBody PengajuanAnggotaDTO pengajuanAnggota, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
            PengajuanAnggotaModel savedPengajuanAnggota = pengajuanAnggotaService.savePengajuanAnggota(pengajuanAnggota);
            return new ResponseEntity<>(savedPengajuanAnggota, HttpStatus.OK);
        }
    }
    
    @PostMapping("/terima/{id}")
    public ResponseEntity<AnggotaModel> terimaPengajuanAnggota(@PathVariable("id") Long id) {
        PengajuanAnggotaModel pengajuanToChange = pengajuanAnggotaService.getPengajuanAnggotaById(id);

        if (pengajuanToChange == null) {
            
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pengajuanToChange.setStatusPengajuan("Pengajuan Diterima");
        pengajuanAnggotaRepository.save(pengajuanToChange);

        AnggotaModel anggotaNew = pengajuanAnggotaService.createAnggota(pengajuanToChange);
        
//        // twillio
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//        // Create Message
//        Message message = Message.creator(
//                // input phone number
//                new com.twilio.type.PhoneNumber("+6287887872067"),
//
//                // Don't change this
//                new com.twilio.type.PhoneNumber("+15075011964"),
//
//                // This is the body of message
//                "Selamat, kamu berhasil menjadi anggota!\n" +
//                        "Username: " + pengajuanToChange.getUsername() + "\n" +
//                        "Password: " + pengajuanToChange.getUsername() + "\n" +
//                        "Silahkan login di https://c01-fe.vercel.app/login/form"
//        ).create();
//        System.out.println(message.getSid());

        return new ResponseEntity<>(anggotaNew, HttpStatus.OK);
    }
    @PostMapping("/tolak/{id}")
    public ResponseEntity<PengajuanAnggotaModel> tolakPengajuanAnggota(@RequestBody UpdateStatusDTO updateStatusDTO, @PathVariable("id") Long id) {
        PengajuanAnggotaModel pengajuanAnggota = pengajuanAnggotaService.getPengajuanAnggotaById(id);

        if (updateStatusDTO.getStatus() == null) {
            updateStatusDTO.setStatus("Ditolak");
        }
        if (pengajuanAnggota == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PengajuanAnggotaModel anggotaUpdated = pengajuanAnggotaService.updateStatus(updateStatusDTO, id);
        return new ResponseEntity<>(anggotaUpdated, HttpStatus.OK);
    }

    @PostMapping("/update/{id}/{status}/{hasilSurvey}")
    public ResponseEntity<PengajuanAnggotaModel> updatePengajuanAnggota(@PathVariable Long id, @PathVariable String status, @PathVariable String hasilSurvey){
        PengajuanAnggotaModel pengajuanToChange = pengajuanAnggotaService.getPengajuanAnggotaById(id);
        // pengajuanAnggotaService.updatePengajuanAnggota(pengajuanToChange, status, hasilSurvey);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
