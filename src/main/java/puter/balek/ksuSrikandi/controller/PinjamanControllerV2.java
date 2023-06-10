package puter.balek.ksuSrikandi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import puter.balek.ksuSrikandi.DTO.PieChartComponentDTO;
import puter.balek.ksuSrikandi.model.PinjamanModel;
import puter.balek.ksuSrikandi.repository.PinjamanRepository;
import puter.balek.ksuSrikandi.service.PinjamanService;

@RestController
@RequestMapping("/api/v2/pinjaman")
public class PinjamanControllerV2 {

    @Autowired
    private PinjamanService pinjamanService;

    @Autowired
    private PinjamanRepository pinjamanRepository;


    @GetMapping("/list-of-all-ids")
    public ResponseEntity<List<Long>> getAllIdPinjaman() {
        List<Long> listId = new ArrayList<>();

        List<PinjamanModel> listPinjaman = pinjamanService.getAllPinjaman();
        if (listPinjaman.size() == 0) {
            return new ResponseEntity<>(listId, HttpStatus.OK);
        }

        for (PinjamanModel x : listPinjaman) {
            listId.add(x.getId());
        }

        return new ResponseEntity<>(listId, HttpStatus.OK);
    }

}

