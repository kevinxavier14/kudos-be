package puter.balek.ksuSrikandi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import puter.balek.ksuSrikandi.DTO.PembayaranDTO;
import puter.balek.ksuSrikandi.model.PembayaranModel;
import puter.balek.ksuSrikandi.service.PembayaranService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v2/pembayaran")
@CrossOrigin(origins = "http://localhost:3000")
public class PembayaranControllerV2 {

    @Autowired
    public PembayaranService pembayaranService;

    @GetMapping("/view/{id}")
    public PembayaranModel getPembayaranById(@PathVariable("id") Long id) {
        try {
            return pembayaranService.getPembayaranById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pembayaran " + id + " not found"
            );
        }
    }

    @GetMapping("/viewallbypinjaman/{id}")
    public List<PembayaranModel> getAllPembayaranByPinjamanId(@PathVariable("id") Long id) {
        return pembayaranService.getAllPembayaranByPinjamanId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<PembayaranModel> savePembayaran(@RequestBody PembayaranDTO pembayaranDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
            PembayaranModel savedPembayaran = pembayaranService.savePembayaran(pembayaranDTO);
            return new ResponseEntity<>(savedPembayaran, HttpStatus.OK);
        }
    }

    @PutMapping("/update-status/{status}/{id}")
    public ResponseEntity<PembayaranModel> updateStatusPembayaran(@PathVariable("status") String status, @PathVariable("id") Long id) {
        pembayaranService.updateStatusPembayaran(status, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list-of-all-ids")
    public ResponseEntity<List<Long>> getAllIdPembayaran() {
        List<Long> listId = new ArrayList<>();

        List<PembayaranModel> listPembayaran = pembayaranService.getAllPembayaran();

        if (listPembayaran.size() == 0) {
            return new ResponseEntity<>(listId, HttpStatus.OK);
        }

        for (PembayaranModel x : listPembayaran) {
            listId.add(x.getId());
        }

        return new ResponseEntity<>(listId, HttpStatus.OK);
    }
}
