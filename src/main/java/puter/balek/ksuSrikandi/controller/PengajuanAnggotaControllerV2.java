package puter.balek.ksuSrikandi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import puter.balek.ksuSrikandi.DTO.PengajuanAnggotaDTO;
import puter.balek.ksuSrikandi.model.PengajuanAnggotaModel;
import puter.balek.ksuSrikandi.repository.PengajuanAnggotaRepository;
import puter.balek.ksuSrikandi.service.PengajuanAnggotaService;

@RestController
@RequestMapping("/api/v2/pengajuan-anggota")
public class PengajuanAnggotaControllerV2 {

    @Autowired
    private PengajuanAnggotaService pengajuanAnggotaService;
    
    @Autowired
    private PengajuanAnggotaRepository pengajuanAnggotaRepository;

    
    @GetMapping("/list-of-all-ids")
    public ResponseEntity<List<Long>> getAllIdPengajuanAnggota() {
        List<Long> listId = new ArrayList<>();

        List<PengajuanAnggotaModel> listPengajuanAnggota = pengajuanAnggotaService.getAllPengajuanAnggota();
        if (listPengajuanAnggota.size() == 0) {
            return new ResponseEntity<>(listId, HttpStatus.OK);
        }

        for (PengajuanAnggotaModel x : listPengajuanAnggota) {
            listId.add(x.getId());
        }

        return new ResponseEntity<>(listId, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<PengajuanAnggotaModel> savePengajuanAnggota(@RequestBody PengajuanAnggotaDTO pengajuanAnggota, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
            PengajuanAnggotaModel savedPengajuanAnggota = pengajuanAnggotaService.savePengajuanAnggota(pengajuanAnggota);
            return new ResponseEntity<>(savedPengajuanAnggota, HttpStatus.CREATED);
        }
    }
}
