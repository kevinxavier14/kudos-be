package puter.balek.ksuSrikandi.controller;

//import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;

import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.JaminanModel;
import puter.balek.ksuSrikandi.model.PengajuanPinjamanModel;
import puter.balek.ksuSrikandi.model.UserModel;
import puter.balek.ksuSrikandi.restmodel.PengajuanPinjamanDTO;
import puter.balek.ksuSrikandi.service.AnggotaService;
import puter.balek.ksuSrikandi.service.JaminanService;
import puter.balek.ksuSrikandi.service.PengajuanPinjamanService;
import puter.balek.ksuSrikandi.service.UserService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/pengajuan-pinjaman")
@CrossOrigin(origins = "http://localhost:3000")
public class PengajuanPinjamanController {

    @Autowired
    private PengajuanPinjamanService pengajuanPinjamanService;

    @Autowired
    private JaminanService jaminanService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private AnggotaService anggotaService;

    //Retrieve
    @GetMapping("/{id}")
    public PengajuanPinjamanModel getPengajuanPinjamanById(@PathVariable("id") Long id) {
        try {
            return pengajuanPinjamanService.getPengajuanPinjamanById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pengajuan Pinjaman " + id + " not found"
            );
        }
    }

    @GetMapping("/" +
            "3/{id}")
    public List<PengajuanPinjamanModel> getAllPengajuanPinjamanAnggota(@PathVariable("id") Long id) {
        return pengajuanPinjamanService.getAllPengajuanPinjamanAnggota(id);
    }

    //Add
    @PostMapping("/add")
    public ResponseEntity<PengajuanPinjamanModel> savePengajuanPinjaman(@RequestBody PengajuanPinjamanModel pengajuanPinjaman) {
        PengajuanPinjamanModel savedPengajuanPinjaman = pengajuanPinjamanService.savePengajuanPinjaman(pengajuanPinjaman);
        JaminanModel jaminan = pengajuanPinjaman.getJaminan();
        JaminanModel savedJaminan = jaminanService.saveJaminan(jaminan);
        pengajuanPinjaman.setJaminan(savedJaminan);
        jaminan.setPengajuanPinjaman(savedPengajuanPinjaman);
        return new ResponseEntity<>(savedPengajuanPinjaman, HttpStatus.CREATED);
    }

    @PutMapping("/updateHasilSurvey/{id}/{hasilSurvey}")
    public ResponseEntity<PengajuanPinjamanModel> updateHasilSurvey(@PathVariable("id") Long id, @PathVariable("hasilSurvey") String hasilSurvey) {
        PengajuanPinjamanModel pengajuanToChange = pengajuanPinjamanService.getPengajuanPinjamanById(id);
        pengajuanPinjamanService.updateHasilSurveyPengajuanPinjaman(pengajuanToChange, hasilSurvey);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Update
    @PutMapping("/update/{id}")
    public PengajuanPinjamanModel updatePengajuanPinjaman(@PathVariable("id") Long id, @RequestBody PengajuanPinjamanModel pengajuanPinjaman){
        try {
            return pengajuanPinjamanService.updatePengajuanPinjaman(id, pengajuanPinjaman);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pengajuan Pinjaman " + id + " not found"
            );
        }
    }

    //Accept Pengajuan
    @PutMapping("/terima/{id}")
    public PengajuanPinjamanModel terimaPengajuanPinjaman(@PathVariable("id") Long id){
        try {
            return pengajuanPinjamanService.terimaPengajuanPinjaman(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pengajuan Pinjaman " + id + " not found"
            );
        }
    }

    //Tolak Pengajuan
    @PutMapping("/tolak/{id}")
    public PengajuanPinjamanModel tolakPengajuanPinjaman(@PathVariable("id") Long id){
        try {
            return pengajuanPinjamanService.tolakPengajuanPinjaman(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pengajuan Pinjaman " + id + " not found"
            );
        }
    }

    // Survey Pengajuan
    @PutMapping("/survey/{id}")
    public PengajuanPinjamanModel surveyPengajuanPinjaman(@PathVariable("id") Long id){
        try {
            return pengajuanPinjamanService.surveyPengajuanPinjaman(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pengajuan Pinjaman " + id + " not found"
            );
        }
    }

    // Get all pengajuan pinjaman base on roles
    @GetMapping("/viewall")
    public ResponseEntity<List<PengajuanPinjamanDTO>> listPenggajuanPinjaman() {
        // Auth
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserModel user = userService.getUserByUsername(username);
        // for local purpose
//        String username = "staff1";
//        UserModel user = userService.getUserByUsername(username);
//        System.out.println(user.getRole());

        // Get all list pengajuan pinjaman
        List<PengajuanPinjamanDTO> listPengajuanPinjamanDTO = new ArrayList<>();
        List<PengajuanPinjamanModel> listPengajuanPinjaman = pengajuanPinjamanService.getAllPengajuanPinjaman();

        // Filter by Angggota
        if (Objects.equals(user.getRole(), "ANGGOTA")) {
            AnggotaModel anggota = anggotaService.getAnggotaByUsername(username);
            List<PengajuanPinjamanModel> listPengajuanByAnggota = anggota.getListPengajuanPinjaman();

            // Iterate through listPengajuanByAngggota and transfer to DTO
            for (int i = 0; i < listPengajuanByAnggota.size(); i++) {
                listPengajuanPinjamanDTO.add(pengajuanPinjamanService.setDTO(listPengajuanByAnggota.get(i)));
            }

            return new ResponseEntity<>(listPengajuanPinjamanDTO, HttpStatus.OK);

        // Filter by Staff / Manajer
        } else if (Objects.equals(user.getRole(), "STAFF") || Objects.equals(user.getRole(), "MANAJER") || Objects.equals(user.getRole(), "SURVEYOR")) {
            for (int i = 0; i < listPengajuanPinjaman.size(); i++) {
                listPengajuanPinjamanDTO.add(pengajuanPinjamanService.setDTO(listPengajuanPinjaman.get(i)));
            }

            return new ResponseEntity<>(listPengajuanPinjamanDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(listPengajuanPinjamanDTO, HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{status}/{id}")
    public ResponseEntity<PengajuanPinjamanModel> updateStatus(@PathVariable("status") String status, @PathVariable("id") Long id){
        PengajuanPinjamanModel pengajuanToChange = pengajuanPinjamanService.getPengajuanPinjamanById(id);
        pengajuanPinjamanService.updateStatusPengajuanPinjaman(pengajuanToChange, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



