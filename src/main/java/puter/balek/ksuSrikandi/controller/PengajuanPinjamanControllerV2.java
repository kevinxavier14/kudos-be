package puter.balek.ksuSrikandi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import puter.balek.ksuSrikandi.model.PengajuanPinjamanModel;
import puter.balek.ksuSrikandi.repository.PengajuanPinjamanRepository;
import puter.balek.ksuSrikandi.service.PengajuanPinjamanService;

@RestController
@RequestMapping("/api/v2/pengajuan-pinjaman")
public class PengajuanPinjamanControllerV2 {

    @Autowired
    private PengajuanPinjamanService pengajuanPinjamanService;

    @Autowired
    private PengajuanPinjamanRepository pengajuanPinjamanRepository;


    @GetMapping("/list-of-all-ids")
    public ResponseEntity<List<String>> getAllIdPengajuanPinjaman() {
        List<String> listId = new ArrayList<>();

        List<PengajuanPinjamanModel> listPengajuanPinjaman = pengajuanPinjamanService.getAllPengajuanPinjaman();
        if (listPengajuanPinjaman.size() == 0) {
            return new ResponseEntity<>(listId, HttpStatus.OK);
        }

        for (PengajuanPinjamanModel x : listPengajuanPinjaman) {
            listId.add(x.getId().toString());
        }

        return new ResponseEntity<>(listId, HttpStatus.OK);
    }
}

