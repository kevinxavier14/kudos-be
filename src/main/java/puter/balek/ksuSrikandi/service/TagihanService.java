package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.DTO.TagihanDTO;
import puter.balek.ksuSrikandi.model.PinjamanModel;
import puter.balek.ksuSrikandi.repository.PinjamanRepository;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Service
public class TagihanService {

    @Autowired
    PinjamanRepository pinjamanRepository;

    public Double calculateNominalTagihan(PinjamanModel pinjamanModel, int tambahanBulan){

        Double nominalTagihan = new Double(0);

        if (pinjamanModel.getJenisPinjaman().equals("musiman")){

            if (pinjamanModel.getSisaBulan() + tambahanBulan == 1){
                nominalTagihan = pinjamanModel.getTagihanMusimanBulanTerakhir();
            }
            else if (pinjamanModel.getSisaBulan() + tambahanBulan <= 0){
            }
            //Bulan Bulan Lain
            else {
                nominalTagihan = pinjamanModel.getTagihanMusimanBiasa();
            }
        }

        //TAHUNAN
        else{
            if (pinjamanModel.getSisaBulan() + tambahanBulan >= 1){
                nominalTagihan = pinjamanModel.getTagihanBulanan();
            }
            //Bulan Bulan Lain
            else {
            }
        }
        return  nominalTagihan;
    }

    public TagihanDTO getNominalTagihan(Long id){
        PinjamanModel pinjamanModel = pinjamanRepository.findById(id).get();

        TagihanDTO tagihanDTO = getNominalTagihan(pinjamanModel);

        return tagihanDTO;
    }


    public TagihanDTO getNominalTagihan(PinjamanModel pinjamanModel){
        TagihanDTO returnTagihan = new TagihanDTO();
        LocalDate nowDate = LocalDate.now();
        LocalDate jatuhTempo = pinjamanModel.getTanggalJatuhTempo().plusDays(1);
        Double nominalTerlambat = new Double(0);
        Double nominalTagihan = new Double(0);

        Period period = Period.between(jatuhTempo, nowDate);
        if (period.isNegative()) {
            int years = period.getYears();
            int months = period.getMonths();
            int late = years * 12 + months;


            for (int i = 0; i <= late; i++){
                nominalTagihan += calculateNominalTagihan(pinjamanModel, i);
                if(i < late){
                    nominalTerlambat += calculateNominalTagihan(pinjamanModel, i);
                }
            }
        }
        else{
            if (pinjamanModel.getJenisPinjaman().equals("angsuran")){
                nominalTagihan = Math.floor(pinjamanModel.getTagihanBulanan());
            }else{
                nominalTagihan = Math.floor(pinjamanModel.getTagihanMusimanBiasa());
            }
        }

        Double denda = new Double(getDenda(pinjamanModel));
        nominalTagihan += denda;
        if (pinjamanModel.getJenisPinjaman().equals("angsuran")){
            returnTagihan.setTagihanBulanan(Math.floor(pinjamanModel.getTagihanBulanan()));
        }else{
            returnTagihan.setTagihanBulanan(Math.floor(pinjamanModel.getTagihanMusimanBiasa()));
        }
        returnTagihan.setTotalTagihan(nominalTagihan);
        returnTagihan.setTagihanBulanSebelumnya(Math.floor(nominalTerlambat));
        returnTagihan.setPenalty(Math.floor(denda));


        return returnTagihan;
    }

    public double getDenda(PinjamanModel pinjaman) {
        LocalDate dateNow = LocalDate.now();
        Long daysBetween = ChronoUnit.DAYS.between(dateNow, pinjaman.getTanggalJatuhTempo());

        // if buat yang kelar
        if (pinjaman.getJenisPinjaman().equals("musiman")){
            if (daysBetween < 0) {
                return 0.003 * Math.abs(daysBetween) * pinjaman.getTagihanMusimanBiasa();
            } else {
                return 0;
            }
        }else{
            if (daysBetween < 0) {
                return 0.003 * Math.abs(daysBetween) * pinjaman.getTagihanBulanan();
            } else {
                return 0;
            }
        }

    }
}
