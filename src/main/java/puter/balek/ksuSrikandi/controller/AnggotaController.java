package puter.balek.ksuSrikandi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.service.AnggotaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/anggota")
public class AnggotaController {
    @Autowired
    private AnggotaService anggotaService;

    @GetMapping("/jumlah-peminjam-chart")
    public List<Object> getJumlahPeminjamData() {
        return anggotaService.getVolumePeminjamChart();
    }

    @GetMapping("/cek-bisa-menggajukan-pinjaman/{username}")
    public Boolean getBisaMengajukanPinjaman(@PathVariable("username") String username) {
        AnggotaModel anggota = anggotaService.getAnggotaByUsername(username);
        return anggotaService.canMakePengajuanPinjaman(anggota);
    }
}
