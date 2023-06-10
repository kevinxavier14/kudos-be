package puter.balek.ksuSrikandi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import puter.balek.ksuSrikandi.model.JaminanModel;
import puter.balek.ksuSrikandi.service.JaminanService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/jaminan")
public class JaminanController {
    @Autowired
    private JaminanService jaminanService;

    @GetMapping("/view/{id}")
    public JaminanModel getJaminanById(@PathVariable("id") Long id) {
        try {
            return  jaminanService.getJaminanById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tidak ada jaminan dengan id " + id
            );
        }
    }

    @PostMapping
    public ResponseEntity<JaminanModel> saveJaminan(@RequestBody JaminanModel jaminan) {
        JaminanModel savedJaminan = jaminanService.saveJaminan(jaminan);
        return new ResponseEntity<>(savedJaminan, HttpStatus.CREATED);
    }
}
