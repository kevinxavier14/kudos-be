package puter.balek.ksuSrikandi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import puter.balek.ksuSrikandi.DTO.PieChartComponentDTO;
import puter.balek.ksuSrikandi.model.JaminanModel;
import puter.balek.ksuSrikandi.model.PengajuanPinjamanModel;
import puter.balek.ksuSrikandi.model.PinjamanModel;
import puter.balek.ksuSrikandi.service.PengajuanPinjamanService;
import puter.balek.ksuSrikandi.service.PinjamanService;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/pinjaman")
@CrossOrigin(origins = "http://localhost:3000")
public class PinjamanController {

    @Autowired
    private PinjamanService pinjamanService;

    @Autowired
    private PengajuanPinjamanService pengajuanPinjamanService;

    @PostMapping("/add/{idPengajuanPinjaman}/{idJaminan}")
    public PinjamanModel savePinjaman(@PathVariable("idPengajuanPinjaman") Long idPengajuanPinjaman, @PathVariable("idJaminan") Long idJaminan){
        PengajuanPinjamanModel pengajuanPinjaman = pengajuanPinjamanService.getPengajuanPinjamanById(idPengajuanPinjaman);
        JaminanModel jaminan = pengajuanPinjaman.getJaminan();
        return pinjamanService.createPinjaman(pengajuanPinjaman, jaminan);
    }

    @GetMapping("/{id}")
    public PinjamanModel getPinjamanById(@PathVariable("id") Long id) {
        try {
            return pinjamanService.getPinjamanById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pinjaman " + id + " not found"
            );
        }
    }

    @GetMapping("/volume-pinjaman-chart")
    public List<Object> getVolumePinjamanData() {
        return pinjamanService.getVolumePinjamanChart();
    }

    @GetMapping("/sisa-pinjaman-chart/{id}")
    public ResponseEntity<Object> getSisaPinjamanChartComponentById(@PathVariable("id") Long id) {
        PinjamanModel pinjaman = pinjamanService.getPinjamanById(id);

        Long sisaPinjamanLong = pinjaman.getSisaPinjaman()/1000000;

        double sisaPinjamanDOuble = (double)sisaPinjamanLong;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String roundedValue = decimalFormat.format(sisaPinjamanDOuble);

        sisaPinjamanLong = Long.parseLong(roundedValue);

        PieChartComponentDTO sisaPinjaman = new PieChartComponentDTO();
        sisaPinjaman.setId("sisa");
        sisaPinjaman.setLabel("sisa");
        sisaPinjaman.setValue(sisaPinjamanLong);
        sisaPinjaman.setColor("hsl(239, 49%, 52%)");

        Long terbayarLong = pinjaman.getNominalTerbayar()/1000000;
        double terbayarDouble = (double) terbayarLong;
        roundedValue = decimalFormat.format(terbayarDouble);
        terbayarLong = Long.parseLong(roundedValue);

        PieChartComponentDTO nominalTerbayar = new PieChartComponentDTO();
        nominalTerbayar.setId("terbayar");
        nominalTerbayar.setLabel("terbayar");
        nominalTerbayar.setValue(terbayarLong);
        nominalTerbayar.setColor("hsl(350, 63%, 57%)");

        PieChartComponentDTO[] data = new PieChartComponentDTO[]{
                sisaPinjaman,
                nominalTerbayar
        };

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/all-sisa-pinjaman-chart")
    public ResponseEntity<Object> getAllSisaPinjamanChartComponent() {
        Long totalSisaPinjaman = 0L;
        Long totalNominalTerbayar = 0L;

        List<PinjamanModel> listPinjaman = pinjamanService.getAllPinjaman();
        for (PinjamanModel x : listPinjaman) {
            totalSisaPinjaman += x.getSisaPinjaman();
            totalNominalTerbayar += x.getNominalTerbayar();
        }

        PieChartComponentDTO sisaPinjaman = new PieChartComponentDTO();
        sisaPinjaman.setId("sisa");
        sisaPinjaman.setLabel("sisa");
        sisaPinjaman.setValue(totalSisaPinjaman);
        sisaPinjaman.setColor("hsl(239, 49%, 52%)");

        PieChartComponentDTO nominalTerbayar = new PieChartComponentDTO();
        nominalTerbayar.setId("terbayar");
        nominalTerbayar.setLabel("terbayar");
        nominalTerbayar.setValue(totalNominalTerbayar);
        nominalTerbayar.setColor("hsl(350, 63%, 57%)");

        PieChartComponentDTO[] data = new PieChartComponentDTO[]{
                sisaPinjaman,
                nominalTerbayar
        };

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
