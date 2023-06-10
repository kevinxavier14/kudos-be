package puter.balek.ksuSrikandi.service;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.DTO.CairkanPinjamanDTO;
import puter.balek.ksuSrikandi.DTO.TerlambatChart;
import puter.balek.ksuSrikandi.model.*;
import puter.balek.ksuSrikandi.repository.PinjamanRepository;
import puter.balek.ksuSrikandi.repository.TerlambatRepository;
import puter.balek.ksuSrikandi.restmodel.ReminderDTO;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.PinjamanModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@EnableScheduling
public class PinjamanService {

    @Autowired
    private PinjamanRepository pinjamanRepository;
    @Autowired
    private TerlambatRepository terlambatRepository;


    private final String ACCOUNT_SID = "ACe49475696df91c9c6f127166bdbd6a17";
    private final String AUTH_TOKEN = "80569ca7a2b46d0b15720c3fbe3a7323";

    public List<PinjamanModel> getAllPinjaman() {
        return pinjamanRepository.findAll();
    }

    public ReminderDTO setReminderDTO(PinjamanModel pinjaman) {
        ReminderDTO reminder = new ReminderDTO();

        reminder.setId(pinjaman.getId());

        reminder.setName(pinjaman.getAnggota().getName());

        BigDecimal nominalKreditFormat = BigDecimal.valueOf(pinjaman.getNominalPinjaman());
        reminder.setNominalPinjaman(String.format("Rp" + "%,.2f", nominalKreditFormat));

        if (pinjaman.getTagihanBulanan() != null) {
            BigDecimal kreditAktiFormat = BigDecimal.valueOf(pinjaman.getTagihanBulanan());
            reminder.setKreditAktif(String.format("Rp" + "%,.2f", kreditAktiFormat));
        } else if (pinjaman.getTagihanMusimanBiasa() != null) {
            BigDecimal kreditAktiFormat = BigDecimal.valueOf(pinjaman.getTagihanMusimanBiasa());
            reminder.setKreditAktif(String.format("Rp" + "%,.2f", kreditAktiFormat));
        } else if (pinjaman.getTagihanMusimanBulanTerakhir() != null) {
            BigDecimal kreditAktiFormat = BigDecimal.valueOf(pinjaman.getTagihanMusimanBulanTerakhir());
            reminder.setKreditAktif(String.format("Rp" + "%,.2f", kreditAktiFormat));
        }

        LocalDate dateNow = LocalDate.now();
        Long daysBetween = ChronoUnit.DAYS.between(dateNow, pinjaman.getTanggalJatuhTempo());
        double denda = 0;

        if (daysBetween > 0) {
            reminder.setDeadline("H-" + daysBetween);
            reminder.setDenda("-");
        } else if (daysBetween == 0) {
            reminder.setDeadline("H");
            reminder.setDenda("-");
        } else {
            reminder.setDeadline("H+" + Math.abs(daysBetween));

            // Masi belum fix
            if (pinjaman.getTagihanBulanan() != null) {
                denda = 0.003 * Math.abs(daysBetween) * pinjaman.getTagihanBulanan();
                reminder.setDenda(String.format("Rp" + "%,.2f", denda));
            } else if (pinjaman.getTagihanMusimanBiasa() != null) {
                denda = 0.003 * Math.abs(daysBetween) * pinjaman.getTagihanMusimanBiasa();
                reminder.setDenda(String.format("Rp" + "%,.2f", denda));
            } else if (pinjaman.getTagihanMusimanBulanTerakhir() != null) {
                denda = 0.003 * Math.abs(daysBetween) * pinjaman.getTagihanMusimanBulanTerakhir();
                reminder.setDenda(String.format("Rp" + "%,.2f", denda));
            }
        }
        reminder.setStatus(pinjaman.getStatusPinjaman());

        return reminder;
    }

//    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Jakarta")
//    public void checkReminder() {
//        List<PinjamanModel> listPinjaman = getAllPinjaman();
//        LocalDate dateNow = LocalDate.now();
//        for (int i = 0; i < listPinjaman.size(); i++) {
//            Long daysBetween = ChronoUnit.DAYS.between(dateNow, listPinjaman.get(i).getTanggalJatuhTempo());
//            if (daysBetween == 1) {
//                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//                // Create Message
//                Message message = Message.creator(
//                        // input phone number
//                        new com.twilio.type.PhoneNumber("+6287887872067"),
//
//                        // Don't change this
//                        new com.twilio.type.PhoneNumber("+15075011964"),
//
//                        // This is the body of message
//                        "Ini adalah pesan pengingat bahwa kamu hampir lewat tanggal jatuh tempo!\n" +
//                                "Akunmu dengan nama " + listPinjaman.get(i).getAnggota().getName() + " masih harus membayar angsuran\n" +
//                                "Silahkan cek di https://c01-fe.vercel.app/reminder"
//                ).create();
//                System.out.println(message.getSid());
//            }
//        }
//    }
//    @Scheduled(fixedRate = 10000)
//    public void testTerlambatChart(){
//
//        LocalDate dateNow = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
//        String formattedMonth = dateNow.format(formatter);
//        TerlambatModel terlambatModel = new TerlambatModel();
//        terlambatModel.setBulan(formattedMonth);
//        terlambatRepository.save(terlambatModel);
//    }

    @Scheduled(cron = "0 1 0 * * *", zone = "Asia/Jakarta")
    public void updateStatusPinjamanRutin() {

        List<PinjamanModel> pinjamanModelList = pinjamanRepository.findAll();

        for (PinjamanModel pinjamanModel : pinjamanModelList) {

            LocalDate dateJatuhTempo = pinjamanModel.getTanggalJatuhTempo();
            LocalDate dateNow = LocalDate.now();
            int comparisonResult = dateNow.compareTo(dateJatuhTempo);

            if (pinjamanModel.getStatusPinjaman().equals("SEHAT")) {
                if (comparisonResult > 0) {
                    // INI KONDISI TELAT BAYAR
                    // Date now after jatuh tempo
                    pinjamanModel.setStatusPinjaman("TERLAMBAT");
                    pinjamanRepository.save(pinjamanModel);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                    String formattedMonth = dateNow.format(formatter);
                    TerlambatModel terlambatModel = terlambatRepository.findByBulan(formattedMonth);

                    //Kalau belum data yang terlambat pada bulan itu
                    if (terlambatModel == null) {

                        terlambatModel = new TerlambatModel();
                        terlambatModel.setBulan(formattedMonth);
                        terlambatModel.setJulahTerlambat(0L);

                    }
                    Long currentTerlambat = terlambatModel.getJulahTerlambat();
                    currentTerlambat += 1;
                    terlambatModel.setJulahTerlambat(currentTerlambat);
                    terlambatRepository.save(terlambatModel);

                }
            }
            if (pinjamanModel.getStatusPinjaman().equals("TERLAMBAT")) {
                if (comparisonResult < 0) {
                    // INI KONDISI UDAH BAYAR
                    // Date now before jatuh tempo
                    pinjamanModel.setStatusPinjaman("SEHAT");
                    pinjamanRepository.save(pinjamanModel);
                }
            }


        }
        System.out.println("Scheduled Update Pinjaman");

    }

    public PinjamanModel getPinjamanById(Long id) {
        return pinjamanRepository.findById(id).orElse(null);
    }


    // PUNYA JONN

    public List<PinjamanModel> getAllPinjam(int status) {

        List<PinjamanModel> listAllPinjaman = pinjamanRepository.findAll();
        List<PinjamanModel> listAktif = new ArrayList<>();
        List<PinjamanModel> listHistory = new ArrayList<>();

        // Kredit belum dicairkan
        if (status == 0) {

            for (PinjamanModel p : listAllPinjaman) {
                if (p.getStatusPinjaman().equals("BELUM CAIR")) {
                    listAktif.add(p);
                }
            }
            return listAktif;

            // aktif
        } else if (status == 1) {

            for (PinjamanModel p : listAllPinjaman) {
                if (p.getStatusPinjaman().equals("SEHAT")) {
                    listAktif.add(p);
                }
            }
            return listAktif;

        } else if (status == 2) {

            for (PinjamanModel p : listAllPinjaman) {
                if (p.getStatusPinjaman().equals("TERLAMBAT")) {
                    listAktif.add(p);
                }
            }
            return listAktif;

        }

        //kredit tidak aktif
        else {

            for (PinjamanModel p : listAllPinjaman) {
                if (p.getStatusPinjaman().equals("SELESAI")) {
                    listHistory.add(p);
                }
            }
            return listHistory;

        }
    }

    public List<PinjamanModel> getAllPinjam(int status, Long anggotaId) {

        // Kredit Aktif
        if (status == 0) {
            return pinjamanRepository.findByAnggotaIdAndStatus(anggotaId, "BELUM CAIR");
        } else if (status == 1) {
            return pinjamanRepository.findByAnggotaIdAndStatus(anggotaId, "SEHAT");
        } else if (status == 2) {
            return pinjamanRepository.findByAnggotaIdAndStatus(anggotaId, "SELESAI");
        }
        //kredit tidak aktif
        else {
            return pinjamanRepository.findByAnggotaIdAndStatus(anggotaId, "TERLAMBAT");
        }
    }


    public List<PinjamanModel> getAllAnggota(int status) {
        return null;
    }

    public PinjamanModel createPinjaman(PengajuanPinjamanModel pengajuanPinjaman, JaminanModel jaminan) {
        PinjamanModel newPinjaman = new PinjamanModel();
        newPinjaman.setAnggota(pengajuanPinjaman.getAnggota());
        newPinjaman.setTanggalCair(LocalDate.now());
        newPinjaman.setTanggalJatuhTempo(LocalDate.now().plusMonths(1));
        newPinjaman.setTanggalSelesai(LocalDate.now().plusMonths(pengajuanPinjaman.getJangkaWaktu()));
        newPinjaman.setTotalBulan(pengajuanPinjaman.getJangkaWaktu());
        newPinjaman.setSisaBulan(Long.valueOf(0));
        newPinjaman.setSisaPinjaman(pengajuanPinjaman.getNominalTerima());
        newPinjaman.setNominalPinjaman(pengajuanPinjaman.getNominalTerima());
        newPinjaman.setNominalTerbayar(Long.valueOf(0));
        newPinjaman.setStatusPinjaman("BELUM CAIR");
        Double bunga = hitungBunga(pengajuanPinjaman, jaminan);
        newPinjaman.setBunga(bunga); //Bikin function bunga
        newPinjaman.setIsPinjamanConfirmedByAnggota(false); // set confirmed pinjaman to false
        newPinjaman.setJenisPinjaman(pengajuanPinjaman.getJenisPinjaman());
        if (pengajuanPinjaman.getJenisPinjaman().equals("angsuran")) {
            newPinjaman.setTagihanBulanan(hitungTagihanBulanan(pengajuanPinjaman, bunga));

        } else {
            newPinjaman.setTagihanMusimanBiasa(hitungTagihanMusimanBiasa(pengajuanPinjaman, bunga));
            newPinjaman.setTagihanMusimanBulanTerakhir(hitungTagihanMusimanBulanTerakhir(pengajuanPinjaman, bunga));
        }
        newPinjaman.setJaminan(jaminan);
        return pinjamanRepository.save(newPinjaman);
    }

    public Double hitungTagihanBulanan(PengajuanPinjamanModel pengajuanPinjaman, Double bunga) {
        return (pengajuanPinjaman.getNominalTerima() + bunga) / pengajuanPinjaman.getJangkaWaktu();
    }

    public Double hitungTagihanMusimanBiasa(PengajuanPinjamanModel pengajuanPinjaman, Double bunga) {
        return bunga / pengajuanPinjaman.getJangkaWaktu();
    }

    public Double hitungTagihanMusimanBulanTerakhir(PengajuanPinjamanModel pengajuanPinjaman, Double bunga) {
        return pengajuanPinjaman.getNominalTerima() + (bunga / pengajuanPinjaman.getJangkaWaktu());
    }

    public Double hitungBunga(PengajuanPinjamanModel pengajuanPinjaman, JaminanModel jaminan) {
        if (pengajuanPinjaman.getJenisPinjaman().equals("angsuran")) {
            if (jaminan.getJenisJaminan().equals("Kendaraan")) {
                if (jaminan.getMerkType().equals("SEDAN/JEEP/MINIBUS")) {
                    if (jaminan.getTahun().equals("2021 - Sekarang")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.12;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.125;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.13;
                        }
                    } else if (jaminan.getTahun().equals("2015 - 2020")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.125;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.135;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.15;
                        }
                    } else if (jaminan.getTahun().equals("2010 - 2014")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.135;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.145;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.155;
                        }
                    } else if (jaminan.getTahun().equals("2005 - 2009")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.14;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.15;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.16;
                        }
                    } else {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.165;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.17;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.175;
                        }
                    }
                } else if (jaminan.getMerkType().equals("TRUCK")) {
                    if (jaminan.getTahun().equals("2021 - Sekarang")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.125;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.13;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.135;
                        }
                    } else if (jaminan.getTahun().equals("2015 - 2020")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.13;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.14;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.15;
                        }
                    } else if (jaminan.getTahun().equals("2010 - 2014")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.135;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.145;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.155;
                        }
                    } else if (jaminan.getTahun().equals("2005 - 2009")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.145;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.15;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.165;
                        }
                    } else {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.1675;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.17;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.175;
                        }
                    }

                } else if (jaminan.getMerkType().equals("PICKUP")) {
                    if (jaminan.getTahun().equals("2021 - Sekarang")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.13;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.14;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.145;
                        }
                    } else if (jaminan.getTahun().equals("2015 - 2020")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.135;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.145;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.15;
                        }
                    } else if (jaminan.getTahun().equals("2010 - 2014")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.15;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.16;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.165;
                        }
                    } else if (jaminan.getTahun().equals("2005 - 2009")) {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.155;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.165;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.17;
                        }
                    } else {
                        if (pengajuanPinjaman.getJangkaWaktu() == 12) {
                            return pengajuanPinjaman.getNominalTerima() * 0.165;
                        } else if (pengajuanPinjaman.getJangkaWaktu() == 24) {
                            return pengajuanPinjaman.getNominalTerima() * 0.175;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.1775;
                        }
                    }

                } else {
                    if (jaminan.getTahun().equals("2021 - Sekarang")) {
                        return pengajuanPinjaman.getNominalTerima() * 0.0175;
                    } else {
                        return pengajuanPinjaman.getNominalTerima() * 0.02;
                    }
                }
            } else {
                return pengajuanPinjaman.getNominalTerima() * 0.02;
            }

        } else {
            if (jaminan.getJenisJaminan().equals("Kendaraan")) {
                if (jaminan.getMerkType().equals("SEDAN/JEEP/MINIBUS") || jaminan.getMerkType().equals("TRUCK") || jaminan.getMerkType().equals("PICKUP")) {
                    if (pengajuanPinjaman.getJangkaWaktu() == 3) {
                        if (jaminan.getTahun().equals("2021 - Sekarang")) {
                            return pengajuanPinjaman.getNominalTerima() * 0.025;
                        } else if (jaminan.getTahun().equals("2015 - 2020")) {
                            return pengajuanPinjaman.getNominalTerima() * 0.025;
                        } else if (jaminan.getTahun().equals("2010 - 2014")) {
                            return pengajuanPinjaman.getNominalTerima() * 0.025;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.0275;
                        }
                    } else {
                        if (jaminan.getTahun().equals("2021 - Sekarang")) {
                            return pengajuanPinjaman.getNominalTerima() * 0.025;
                        } else if (jaminan.getTahun().equals("2015 - 2020")) {
                            return pengajuanPinjaman.getNominalTerima() * 0.025;
                        } else if (jaminan.getTahun().equals("2010 - 2014")) {
                            return pengajuanPinjaman.getNominalTerima() * 0.0275;
                        } else {
                            return pengajuanPinjaman.getNominalTerima() * 0.03;
                        }
                    }
                } else {
                    return pengajuanPinjaman.getNominalTerima() * 0.03;
                }
            } else {
                return pengajuanPinjaman.getNominalTerima() * 0.03;
            }
        }
    }

    public PinjamanModel mencairkanPinjaman(CairkanPinjamanDTO cairkanPinjamanDTO) {

        PinjamanModel pinjamanModel = pinjamanRepository.findById(cairkanPinjamanDTO.getIdPinjaman()).get();

        pinjamanModel.setStatusPinjaman("SEHAT");
        pinjamanModel.setBuktiPencairanPinjaman(cairkanPinjamanDTO.getImagePencairan());
        pinjamanRepository.save(pinjamanModel);

        return pinjamanModel;

    }

    public PinjamanModel confirmPencairanDanaPinjaman(Long id) {
        PinjamanModel pinjamanModel = pinjamanRepository.findById(id).get();
        pinjamanModel.setIsPinjamanConfirmedByAnggota(true);
        return pinjamanRepository.save(pinjamanModel);
    }

    public List<Object> getVolumePinjamanChart() {
        // hsla(242, 100%, 68%, 1)
        List<PinjamanModel> allPinjaman = pinjamanRepository.findAll();
        Double vol01 = Double.valueOf(0);
        Double vol02 = Double.valueOf(0);
        Double vol03 = Double.valueOf(0);
        Double vol04 = Double.valueOf(0);
        Double vol05 = Double.valueOf(0);
        Double vol06 = Double.valueOf(0);
        Double vol07 = Double.valueOf(0);
        Double vol08 = Double.valueOf(0);
        Double vol09 = Double.valueOf(0);
        Double vol10 = Double.valueOf(0);
        Double vol11 = Double.valueOf(0);
        Double vol12 = Double.valueOf(0);
        Double vol13 = Double.valueOf(0);

        LocalDate now = LocalDate.now();

        // Loop 12 bulan sebelum
        vol01 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(12));

        // Loop 11 bulan sebelum
        vol02 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(11));
        vol03 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(10));
        vol04 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(9));
        vol05 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(8));
        vol06 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(7));
        vol07 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(6));
        vol08 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(5));
        vol09 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(4));
        vol10 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(3));
        vol11 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(2));
        vol12 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(1));
        vol13 = findActivePinjamanAtSpecificMonth(allPinjaman, Long.valueOf(0));

        List<Object> result = new ArrayList<Object>();

        result.add(reformatDate(getMonthYear(now.minusMonths(12))));
        result.add(vol01);

        result.add(reformatDate(getMonthYear(now.minusMonths(11))));
        result.add(vol02);

        result.add(reformatDate(getMonthYear(now.minusMonths(10))));
        result.add(vol03);

        result.add(reformatDate(getMonthYear(now.minusMonths(9))));
        result.add(vol04);

        result.add(reformatDate(getMonthYear(now.minusMonths(8))));
        result.add(vol05);

        result.add(reformatDate(getMonthYear(now.minusMonths(7))));
        result.add(vol06);

        result.add(reformatDate(getMonthYear(now.minusMonths(6))));
        result.add(vol07);

        result.add(reformatDate(getMonthYear(now.minusMonths(5))));
        result.add(vol08);

        result.add(reformatDate(getMonthYear(now.minusMonths(4))));
        result.add(vol09);

        result.add(reformatDate(getMonthYear(now.minusMonths(3))));
        result.add(vol10);

        result.add(reformatDate(getMonthYear(now.minusMonths(2))));
        result.add(vol11);

        result.add(reformatDate(getMonthYear(now.minusMonths(1))));
        result.add(vol12);

        result.add(reformatDate(getMonthYear(now.minusMonths(0))));
        result.add(vol13);

        return result;
    }

    public Double findActivePinjamanAtSpecificMonth(List<PinjamanModel> allPinjaman, Long monthDiff) {
        Double result = Double.valueOf(0);
        LocalDate now = LocalDate.now();
        LocalDate iteratedDate = now.minusMonths(monthDiff).with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(iteratedDate);

        for (PinjamanModel pinjaman : allPinjaman) {
            if (iteratedDate.isAfter(pinjaman.getTanggalCair()) && iteratedDate.isBefore(pinjaman.getTanggalSelesai())) {
                result += pinjaman.getNominalPinjaman();
            }
        }

            result = result / 1000000;
            double doubleResult = (double)result;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            String roundedValue = decimalFormat.format(doubleResult);

            result = Double.parseDouble(roundedValue);
        return result;
    }

    public String getMonthYear(LocalDate date) {
        String month = String.valueOf(date.getMonth());
        String year = String.valueOf(date.getYear());
        return month + "-" + year;


    }

    public List<TerlambatChart> terlambatChart() {

        LocalDate dateNeeded1 = LocalDate.now().minusMonths(12);

        List<TerlambatChart> returnList = new ArrayList<TerlambatChart>();

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM yyyy");

        for (long i = 12; i > 0; i--) {
            LocalDate dateNeeded = dateNeeded1.plusMonths(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            String dateMonth = dateNeeded.format(formatter);
            System.out.println(dateMonth);
            TerlambatModel terlambatModel = terlambatRepository.findByBulan(dateMonth);
            if (terlambatModel != null) {
                TerlambatChart terlambatChartnew = new TerlambatChart();

                LocalDate date = LocalDate.parse(terlambatModel.getBulan() + "-01", inputFormatter);
                String output = date.format(outputFormatter);

                terlambatChartnew.setBulan(output);
                terlambatChartnew.setJumlah(terlambatModel.getJulahTerlambat());
                returnList.add(terlambatChartnew);
            }

        }
        return returnList;
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
