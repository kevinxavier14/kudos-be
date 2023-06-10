package puter.balek.ksuSrikandi.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import puter.balek.ksuSrikandi.classDTO.KaryawanUpdateProfileDTO;
import puter.balek.ksuSrikandi.configJWT.RegisterKaryawanRequest;
import puter.balek.ksuSrikandi.model.SurveyorModel;
import puter.balek.ksuSrikandi.model.UserModel;
import puter.balek.ksuSrikandi.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class UpdateProfileController {

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

//    @PostMapping(value = "/profile/karyawan/update")
//    private UserModel profileKaryawan(@Valid @RequestBody KaryawanUpdateProfileDTO pas, BindingResult bindingResult, HttpServletRequest request) {
//
//
//        if (bindingResult.hasFieldErrors()) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
//        } else {
//
////            if (pas.getPasswordBaru() != null && pas.getPasswordBaruKonfirmasi() != null && pas.getPasswordLama() != null){
////
////            }
////            if(pas.getUsername().equals(userService.getCredential(request).getUsername())){
////                RegisterKaryawanRequest registerKaryawanRequest = new RegisterKaryawanRequest();
////
////                return null;
////
////            }
////            else{
////                throw new ResponseStatusException(
////                        HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
////            }
//
//        }
//
//    }
}
