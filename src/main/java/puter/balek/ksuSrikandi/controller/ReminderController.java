package puter.balek.ksuSrikandi.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
//import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.PinjamanModel;
import puter.balek.ksuSrikandi.model.UserModel;
import puter.balek.ksuSrikandi.restmodel.ReminderDTO;
import puter.balek.ksuSrikandi.service.AnggotaService;
import puter.balek.ksuSrikandi.service.PinjamanService;
import puter.balek.ksuSrikandi.service.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/reminder")
@CrossOrigin(origins = "http://localhost:3000")
public class ReminderController {
    @Autowired
    private AnggotaService anggotaService;

    @Autowired
    private PinjamanService pinjamanService;

    @Autowired
    private UserService userService;

    private final String ACCOUNT_SID = "ACe49475696df91c9c6f127166bdbd6a17";
    private final String AUTH_TOKEN = "80569ca7a2b46d0b15720c3fbe3a7323";


    @GetMapping("/viewall")
    public ResponseEntity<List<ReminderDTO>> listReminder(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserModel user = userService.getUserByUsername(username);

//        for local purpose
//        String username = "kepin";
//        UserModel user = userService.getUserByUsername(username);

        List<ReminderDTO> listReminderDTO = new ArrayList<>();
        LocalDate dateNow = LocalDate.now();

        if ((Objects.equals(user.getRole(), "STAFF")) || (Objects.equals(user.getRole(), "MANAJER"))) {
            List<PinjamanModel> listPinjaman = pinjamanService.getAllPinjaman();

            return getListResponseEntity(listReminderDTO, dateNow, listPinjaman);

        } else if (Objects.equals(user.getRole(), "ANGGOTA")) {
            AnggotaModel anggota = anggotaService.getAnggotaByUsername(username);
            List<PinjamanModel> listPinjaman = anggota.getListPinjaman();

            return getListResponseEntity(listReminderDTO, dateNow, listPinjaman);
        }
        else {
            return new ResponseEntity<>(listReminderDTO, HttpStatus.OK);
        }
    }

    private ResponseEntity<List<ReminderDTO>> getListResponseEntity(List<ReminderDTO> listReminderDTO, LocalDate dateNow, List<PinjamanModel> listPinjaman) {
        for (int i = 0; i < listPinjaman.size(); i++){
            Long daysBetween = ChronoUnit.DAYS.between(dateNow, listPinjaman.get(i).getTanggalJatuhTempo());
            if (daysBetween < 4 && !Objects.equals(listPinjaman.get(i).getStatusPinjaman(), "SELESAI")) {
                listReminderDTO.add(pinjamanService.setReminderDTO(listPinjaman.get(i)));
            }
        }
        return new ResponseEntity<>(listReminderDTO, HttpStatus.OK);
    }

    @PostMapping("/messagegateway/{id}")
    public void reminderSMS(@PathVariable("id") Long id) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserModel user = userService.getUserByUsername(username);

        PinjamanModel pinjaman = pinjamanService.getPinjamanById(id);

        if (Objects.equals(user.getRole(), "STAFF") || Objects.equals(user.getRole(), "MANAJER")) {
            // twillio
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            // Create Message
            Message message = Message.creator(
                    // input phone number
                    new com.twilio.type.PhoneNumber("+6287887872067"),

                    // Don't change this
                    new com.twilio.type.PhoneNumber("+15075011964"),

                    // This is the body of message
                    "Ini adalah pesan pengingat bahwa kamu hampir lewat tanggal jatuh tempo!\n" +
                            "Akunmu dengan nama " + pinjaman.getAnggota().getName() + " masih harus membayar kredit aktif\n" +
                            "Silahkan cek di https://c01-fe.vercel.app/reminder"
            ).create();
            System.out.println(message.getSid());
        }
    }
}
