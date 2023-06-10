package puter.balek.ksuSrikandi.restController;

//import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import puter.balek.ksuSrikandi.DTO.CairkanPinjamanDTO;
import puter.balek.ksuSrikandi.DTO.TagihanDTO;
import puter.balek.ksuSrikandi.DTO.TerlambatChart;
import puter.balek.ksuSrikandi.configJWT.RegisterKaryawanRequest;
import puter.balek.ksuSrikandi.model.*;
import puter.balek.ksuSrikandi.service.PinjamanService;
import puter.balek.ksuSrikandi.service.TagihanService;
import puter.balek.ksuSrikandi.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class PinjamanRestController {

    @Autowired
    PinjamanService pinjamanService;

    @Autowired
    UserService userService;

    @Autowired
    TagihanService tagihanService;

    @GetMapping("/pinjaman/viewall/aktif")
    private List<PinjamanModel> viewAllPinjamanAktif(@RequestHeader(value = "Authorization", required = false) String authorizationHeader){

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserModel user = userService.getUserByUsername(username);

        if(
                user.getRole().equals("ADMIN")
                || user.getRole().equals("MANAJER")
                || user.getRole().equals("STAFF")
                || user.getRole().equals("SURVEYOR")

        ){

            return pinjamanService.getAllPinjam(1);
        }

        else if (user.getRole().equals("ANGGOTA")){
            return pinjamanService.getAllPinjam(1, user.getId());
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
        }

    }
    @GetMapping("/pinjaman/viewall/history")
    private List<PinjamanModel> viewAllPinjamanHistory(@RequestHeader(value = "Authorization", required = false) String authorizationHeader){

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserModel user = userService.getUserByUsername(username);

        if(
                user.getRole().equals("ADMIN")
                        || user.getRole().equals("MANAJER")
                        || user.getRole().equals("STAFF")
                        || user.getRole().equals("SURVEYOR")

        ){

            return pinjamanService.getAllPinjam(2);
        }

        else if (user.getRole().equals("ANGGOTA")){
            return pinjamanService.getAllPinjam(2, user.getId());
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
        }

    }

    @GetMapping("/pinjaman/viewall/terlambat")
    private List<PinjamanModel> viewAllPinjamanTerlambat(@RequestHeader(value = "Authorization", required = false) String authorizationHeader){

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserModel user = userService.getUserByUsername(username);

        if(
                user.getRole().equals("ADMIN")
                        || user.getRole().equals("MANAJER")
                        || user.getRole().equals("STAFF")
                        || user.getRole().equals("SURVEYOR")

        ){

            return pinjamanService.getAllPinjam(3);
        }

        else if (user.getRole().equals("ANGGOTA")){
            return pinjamanService.getAllPinjam(3, user.getId());
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
        }

    }

    @GetMapping("/pinjaman/viewall/belum-cair")
    private List<PinjamanModel> viewAllPinjamanBelumDicairkan(@RequestHeader(value = "Authorization", required = false) String authorizationHeader){

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserModel user = userService.getUserByUsername(username);

        if(
                user.getRole().equals("ADMIN")
                        || user.getRole().equals("MANAJER")
                        || user.getRole().equals("STAFF")
                        || user.getRole().equals("SURVEYOR")

        ){

            return pinjamanService.getAllPinjam(0);
        }

        else if (user.getRole().equals("ANGGOTA")){
            return pinjamanService.getAllPinjam(0, user.getId());
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
        }

    }

    @GetMapping("/pinjaman/terlambat-chart")
    private List<TerlambatChart> getPinjamanTerlambat(@RequestHeader(value = "Authorization", required = false) String authorizationHeader){

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserModel user = userService.getUserByUsername(username);

        if(
                user.getRole().equals("ADMIN")
                        || user.getRole().equals("MANAJER")
                        || user.getRole().equals("STAFF")
                        || user.getRole().equals("SURVEYOR")

        ){

            return pinjamanService.terlambatChart();
        }

        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Bukan Karyawan");
        }

    }

    @GetMapping("/pinjaman/tagihan/{id}")
    private TagihanDTO getTagihanById(@PathVariable("id") Long id){

        try {
            return tagihanService.getNominalTagihan(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pinjaman " + id + " not found"
            );
        }

    }

    @PostMapping("/pencairan-pinjaman")
    public PinjamanModel savePinjaman(@Valid @RequestBody CairkanPinjamanDTO cairkanPinjamanDTO){
        PinjamanModel pinjamanModel = pinjamanService.mencairkanPinjaman(cairkanPinjamanDTO);
        return pinjamanModel;
    }

    @PostMapping("/pinjaman/confirm-pencairan/{id}")
    public PinjamanModel confirmPencairanPinjaman(@PathVariable("id") Long id){
        PinjamanModel pinjamanModel = pinjamanService.confirmPencairanDanaPinjaman(id);
        return pinjamanModel;
    }



}
