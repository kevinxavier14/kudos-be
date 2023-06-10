package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.PinjamanModel;
import puter.balek.ksuSrikandi.repository.AnggotaRepository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AnggotaService {
    @Autowired
    AnggotaRepository anggotaRepository;

    public AnggotaModel getAnggotaByUsername(String username) {
        return anggotaRepository.findByUsername(username);
    }

    public List<Object> getVolumePeminjamChart() {
        List<AnggotaModel> allAnggota = anggotaRepository.findAll();
        Long vol01 = Long.valueOf(0);
        Long vol02 = Long.valueOf(0);
        Long vol03 = Long.valueOf(0);
        Long vol04 = Long.valueOf(0);
        Long vol05 = Long.valueOf(0);
        Long vol06 = Long.valueOf(0);
        Long vol07 = Long.valueOf(0);
        Long vol08 = Long.valueOf(0);
        Long vol09 = Long.valueOf(0);
        Long vol10 = Long.valueOf(0);
        Long vol11 = Long.valueOf(0);
        Long vol12 = Long.valueOf(0);
        Long vol13 = Long.valueOf(0);
        List<Object> result = new ArrayList<Object>();
        LocalDate now = LocalDate.now();

        // Loop untuk 12 bulan sebelumnya
        vol01 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(12));
        result.add(reformatDate(getMonthYear(now.minusMonths(12))));
        result.add(vol01);

        vol02 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(11));
        result.add(reformatDate(getMonthYear(now.minusMonths(11))));
        result.add(vol02);

        vol03 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(10));
        result.add(reformatDate(getMonthYear(now.minusMonths(10))));
        result.add(vol03);

        vol04 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(9));
        result.add(reformatDate(getMonthYear(now.minusMonths(9))));
        result.add(vol04);

        vol05 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(8));
        result.add(reformatDate(getMonthYear(now.minusMonths(8))));
        result.add(vol05);

        vol06 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(7));
        result.add(reformatDate(getMonthYear(now.minusMonths(7))));
        result.add(vol06);

        vol07 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(6));
        result.add(reformatDate(getMonthYear(now.minusMonths(6))));
        result.add(vol07);

        vol08 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(5));
        result.add(reformatDate(getMonthYear(now.minusMonths(5))));
        result.add(vol08);

        vol09 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(4));
        result.add(reformatDate(getMonthYear(now.minusMonths(4))));
        result.add(vol09);

        vol10 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(3));
        result.add(reformatDate(getMonthYear(now.minusMonths(3))));
        result.add(vol10);

        vol11 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(2));
        result.add(reformatDate(getMonthYear(now.minusMonths(2))));
        result.add(vol11);

        vol12 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(1));
        result.add(reformatDate(getMonthYear(now.minusMonths(1))));
        result.add(vol12);

        vol13 = findActivePeminjamAtSpecificMonth(allAnggota, Long.valueOf(0));
        result.add(reformatDate(getMonthYear(now.minusMonths(0))));
        result.add(vol13);

        return result;
    }

    public Long findActivePeminjamAtSpecificMonth(List<AnggotaModel> allAnggota, Long monthDiff) {
        Long jumlahPeminjam = Long.valueOf(0);
        for (AnggotaModel anggota : allAnggota) {
            LocalDate now = LocalDate.now();
            LocalDate iteratedDate = now.minusMonths(monthDiff).with(TemporalAdjusters.lastDayOfMonth());
            for (PinjamanModel pinjaman : anggota.getListPinjaman()) {
                if (iteratedDate.isAfter(pinjaman.getTanggalCair()) && iteratedDate.isBefore(pinjaman.getTanggalSelesai())) {
                    jumlahPeminjam += 1;
                    break;
                }
            }
        }
        return jumlahPeminjam;
    }

    public String getMonthYear(LocalDate date) {
        String month = String.valueOf(date.getMonth());
        String year = String.valueOf(date.getYear());
        return month + "-" + year;
    }

    public Boolean canMakePengajuanPinjaman(AnggotaModel anggota) {
        for (PinjamanModel pinjaman : anggota.getListPinjaman()) {
            if ((pinjaman.getStatusPinjaman().equals("SELESAI")) == false) {
                return false;
            }
        }
        return true;
    }

    public String reformatDate(String rawDate) {
        String[] split = rawDate.split("-");
        String reformattedDate;

        if (split[0].equals("JANUARY")) {
            reformattedDate = "Jan " + split[1];
        } else if (split[0].equals("FEBRUARY")) {
            reformattedDate = "Feb " + split[1];
        } else if (split[0].equals("MARCH")) {
            reformattedDate = "Mar " + split[1];
        } else if (split[0].equals("APRIL")) {
            reformattedDate = "Apr " + split[1];
        } else if (split[0].equals("MAY")) {
            reformattedDate = "May " + split[1];
        } else if (split[0].equals("JUNE")) {
            reformattedDate = "Jun " + split[1];
        } else if (split[0].equals("JULY")) {
            reformattedDate = "Jul " + split[1];
        } else if (split[0].equals("AUGUST")) {
            reformattedDate = "Aug " + split[1];
        } else if (split[0].equals("SEPTEMBER")) {
            reformattedDate = "Sep " + split[1];
        } else if (split[0].equals("OCTOBER")) {
            reformattedDate = "Oct " + split[1];
        } else if (split[0].equals("NOVEMBER")) {
            reformattedDate = "Nov " + split[1];
        } else {
            reformattedDate = "Dec " + split[1];
        }
        return reformattedDate;
    }
}
