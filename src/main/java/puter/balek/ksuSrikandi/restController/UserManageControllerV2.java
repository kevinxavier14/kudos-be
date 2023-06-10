package puter.balek.ksuSrikandi.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import puter.balek.ksuSrikandi.classDTO.KaryawanDTO;
import puter.balek.ksuSrikandi.classDTO.KaryawanUpdateProfileDTO;
import puter.balek.ksuSrikandi.configJWT.RegisterKaryawanRequest;
import puter.balek.ksuSrikandi.model.*;
import puter.balek.ksuSrikandi.model.AnggotaDTO;
import puter.balek.ksuSrikandi.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class UserManageControllerV2 {
    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @Autowired
    AdminService adminService;

    @Autowired
    ManajerService manajerService;

    @Autowired
    SurveyorService surveyorService;

    @GetMapping("/listall/karyawan")
    public ResponseEntity<List<String>> getAllKarywan() {

        List<String> usernameKaryawanList = new ArrayList<String>();

        for (ManajerModel karyawanModel : manajerService.getAllManajer()){
            usernameKaryawanList.add(karyawanModel.getUsername());
        }

        for (AdminModel karyawanModel : adminService.getAllAdmin()){
            usernameKaryawanList.add(karyawanModel.getUsername());
        }

        for (StaffModel karyawanModel : staffService.getAllStaff()){
            usernameKaryawanList.add(karyawanModel.getUsername());
        }

        for (SurveyorModel karyawanModel : surveyorService.getAllSurveyor()){
            usernameKaryawanList.add(karyawanModel.getUsername());
        }


        return new ResponseEntity<>(usernameKaryawanList, HttpStatus.OK);
    }

    @GetMapping("/listall/anggota")
    public ResponseEntity<List<String>> getAllAnggota() {

        List<String> usernameAnggotaList = new ArrayList<String>();

        List<AnggotaModel> lisAnggotaModel = userService.getAllAnggota();
        if (lisAnggotaModel.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (AnggotaModel x : lisAnggotaModel) {
            usernameAnggotaList.add(x.getUsername());
        }

        return new ResponseEntity<>(usernameAnggotaList, HttpStatus.OK);

    }



}
