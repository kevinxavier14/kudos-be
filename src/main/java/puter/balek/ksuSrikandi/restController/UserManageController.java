package puter.balek.ksuSrikandi.restController;

//import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1")
public class UserManageController {

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


//    DIBAWAH INI
//    ADALAH CONTROLLER KARYAWAN

    @GetMapping("/user/staff/{username}")
    private KaryawanDTO getStaff(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @PathVariable("username") String username){

        if(
                userService.getCredential(authorizationHeader).getRole().equals("ADMIN")
                        || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")
                        || userService.getCredential(authorizationHeader).getUsername().equals(username)
        ){
            UserModel findUser = userService.getUserByUsername(username);
            System.out.println(findUser.getUsername());

            if ((findUser != null) ){
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(findUser.getId());
                karyawanDTO.setRole(findUser.getRole());
                karyawanDTO.setNik(findUser.getNik());
                karyawanDTO.setUsername(findUser.getUsername());
                karyawanDTO.setName(findUser.getName());
                karyawanDTO.setTempatTanggalLahir(findUser.getTempatTanggalLahir());

                karyawanDTO.setNoPegawai(findUser.getPassword());
                if (findUser instanceof ManajerModel){
                    karyawanDTO.setNoPegawai(((ManajerModel) findUser).getNoPegawai());
                }else if (findUser instanceof AdminModel){
                    karyawanDTO.setNoPegawai(((AdminModel) findUser).getNoPegawai());

                }else if (findUser instanceof StaffModel){
                    karyawanDTO.setNoPegawai(((StaffModel) findUser).getNoPegawai());

                }else if (findUser instanceof SurveyorModel){
                    karyawanDTO.setNoPegawai(((SurveyorModel) findUser).getNoPegawai());
                }


                return karyawanDTO;
            }

            return null;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Tidak Dapat Akses Username ini");
        }

    }

    @PostMapping("/user/delete/{role}/{id}")
    private UserModel deleteStaff(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @PathVariable("role") String role,
                                   @PathVariable("id") Long id   ){

        if(
                userService.getCredential(authorizationHeader).getRole().equals("ADMIN")
                        || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")
        ){
            return userService.deleteUser(role.toUpperCase(), id);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Tidak Dapat Akses Username ini");
        }

    }

    @PostMapping(value = "/user/staff/add")
    private UserModel registerstaff(@Valid @RequestBody RegisterKaryawanRequest pas, BindingResult bindingResult, HttpServletRequest request) {


        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {

            return userService.addUser(pas, pas.getRole().toUpperCase());
        }

    }


    @GetMapping("/user/staff/viewall")
    private List<KaryawanDTO> viewAllStaff(@RequestHeader(value = "Authorization", required = false) String authorizationHeader){

        if(userService.getCredential(authorizationHeader).getRole().equals("ADMIN") || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")){
            List<KaryawanDTO> karyawanDTOList = new ArrayList<KaryawanDTO>();

            for (ManajerModel karyawanModel : manajerService.getAllManajer()){
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(karyawanModel.getId());
                karyawanDTO.setRole(karyawanModel.getRole());
                karyawanDTO.setNik(karyawanModel.getNik());
                karyawanDTO.setUsername(karyawanModel.getUsername());
                karyawanDTO.setName(karyawanModel.getName());
                karyawanDTO.setTempatTanggalLahir(karyawanModel.getTempatTanggalLahir());
                karyawanDTO.setNoPegawai(karyawanModel.getNoPegawai());
                karyawanDTO.setPassword(karyawanModel.getPassword());

                karyawanDTOList.add(karyawanDTO);
            }

            for (AdminModel karyawanModel : adminService.getAllAdmin()){
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(karyawanModel.getId());
                karyawanDTO.setRole(karyawanModel.getRole());
                karyawanDTO.setNik(karyawanModel.getNik());
                karyawanDTO.setUsername(karyawanModel.getUsername());
                karyawanDTO.setName(karyawanModel.getName());
                karyawanDTO.setTempatTanggalLahir(karyawanModel.getTempatTanggalLahir());
                karyawanDTO.setNoPegawai(karyawanModel.getNoPegawai());
                karyawanDTO.setPassword(karyawanModel.getPassword());

                karyawanDTOList.add(karyawanDTO);
            }

            for (StaffModel karyawanModel : staffService.getAllStaff()){
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(karyawanModel.getId());
                karyawanDTO.setRole(karyawanModel.getRole());
                karyawanDTO.setNik(karyawanModel.getNik());
                karyawanDTO.setUsername(karyawanModel.getUsername());
                karyawanDTO.setName(karyawanModel.getName());
                karyawanDTO.setTempatTanggalLahir(karyawanModel.getTempatTanggalLahir());
                karyawanDTO.setNoPegawai(karyawanModel.getNoPegawai());
                karyawanDTO.setPassword(karyawanModel.getPassword());

                karyawanDTOList.add(karyawanDTO);
            }

            for (SurveyorModel karyawanModel : surveyorService.getAllSurveyor()){
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(karyawanModel.getId());
                karyawanDTO.setRole(karyawanModel.getRole());
                karyawanDTO.setNik(karyawanModel.getNik());
                karyawanDTO.setUsername(karyawanModel.getUsername());
                karyawanDTO.setName(karyawanModel.getName());
                karyawanDTO.setTempatTanggalLahir(karyawanModel.getTempatTanggalLahir());
                karyawanDTO.setNoPegawai(karyawanModel.getNoPegawai());
                karyawanDTO.setPassword(karyawanModel.getPassword());

                karyawanDTOList.add(karyawanDTO);
            }


            return karyawanDTOList;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
        }

    }

    @GetMapping("/user/karyawan/{role}/{id}")
    private KaryawanDTO getKaryawan(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable("id") Long id,
            @PathVariable("role") String role){

        if(
                userService.getCredential(authorizationHeader).getRole().equals("ADMIN")
                        || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")
        ){

            if (role.toUpperCase().equals("ADMIN")){
                AdminModel karyawanModel = adminService.getAdminById(id);
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(karyawanModel.getId());
                karyawanDTO.setRole(karyawanModel.getRole());
                karyawanDTO.setNik(karyawanModel.getNik());
                karyawanDTO.setUsername(karyawanModel.getName());
                karyawanDTO.setName(karyawanModel.getName());
                karyawanDTO.setTempatTanggalLahir(karyawanModel.getTempatTanggalLahir());
                karyawanDTO.setNoPegawai(karyawanModel.getNoPegawai());
                karyawanDTO.setPassword(karyawanModel.getPassword());
                return karyawanDTO;

            }else if (role.toUpperCase().equals("MANAJER")){
                ManajerModel karyawanModel = manajerService.getManajerById(id);
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(karyawanModel.getId());
                karyawanDTO.setRole(karyawanModel.getRole());
                karyawanDTO.setNik(karyawanModel.getNik());
                karyawanDTO.setUsername(karyawanModel.getName());
                karyawanDTO.setName(karyawanModel.getName());
                karyawanDTO.setTempatTanggalLahir(karyawanModel.getTempatTanggalLahir());
                karyawanDTO.setNoPegawai(karyawanModel.getNoPegawai());
                karyawanDTO.setPassword(karyawanModel.getPassword());
                return karyawanDTO;

            }else if (role.toUpperCase().equals("STAFF")){
                StaffModel karyawanModel = staffService.getStaffById(id);
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(karyawanModel.getId());
                karyawanDTO.setRole(karyawanModel.getRole());
                karyawanDTO.setNik(karyawanModel.getNik());
                karyawanDTO.setUsername(karyawanModel.getName());
                karyawanDTO.setName(karyawanModel.getName());
                karyawanDTO.setTempatTanggalLahir(karyawanModel.getTempatTanggalLahir());
                karyawanDTO.setNoPegawai(karyawanModel.getNoPegawai());
                karyawanDTO.setPassword(karyawanModel.getPassword());
                return karyawanDTO;

            }else if (role.toUpperCase().equals("SURVEYOR")){
                SurveyorModel karyawanModel = surveyorService.getSurveyorById(id);
                KaryawanDTO karyawanDTO = new KaryawanDTO();
                karyawanDTO.setId(karyawanModel.getId());
                karyawanDTO.setRole(karyawanModel.getRole());
                karyawanDTO.setNik(karyawanModel.getNik());
                karyawanDTO.setUsername(karyawanModel.getName());
                karyawanDTO.setName(karyawanModel.getName());
                karyawanDTO.setTempatTanggalLahir(karyawanModel.getTempatTanggalLahir());
                karyawanDTO.setNoPegawai(karyawanModel.getNoPegawai());
                karyawanDTO.setPassword(karyawanModel.getPassword());
                return karyawanDTO;

            }

            return null;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Tidak Dapat Akses Username ini");
        }

    }

//    DIBAWAH INI
//    CONTROLLER ANGGOTA

    @GetMapping("/user/anggota/viewall")
    private List<AnggotaModel> viewAllAnggota(@RequestHeader(value = "Authorization", required = false) String authorizationHeader){

        if(userService.getCredential(authorizationHeader).getRole().equals("ADMIN") || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")){

            return userService.getAllAnggota();
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
        }

    }

    @PostMapping(value = "/user/anggota/add")
    private AnggotaModel registerAnggota(@Valid @RequestBody AnggotaDTO pas, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
//            if(userService.getCredential(request).getRole().equals("ADMIN")){
//
//            }

            return userService.addAnggota(pas);
        }

    }

    @GetMapping("/user/anggota/{username}")
    private AnggotaModel getAnggota(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @PathVariable("username") String username){


        if(
                userService.getCredential(authorizationHeader).getRole().equals("ADMIN")
                        || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")
                        || userService.getCredential(authorizationHeader).getUsername().equals(username)
        ){
            AnggotaModel findUser = userService.getAnggotaByUsername(username);

            return findUser;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Tidak Dapat Akses Username ini");
        }

    }

    @PostMapping(value = "/user/admin/update")
    private AdminModel updateAdmin(@Valid @RequestBody RegisterKaryawanRequest pas, BindingResult bindingResult, HttpServletRequest request) {


        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
            if(userService.getCredential(request).getRole().equals("ADMIN") || userService.getCredential(request).getRole().equals("MANAJER")){
                return (AdminModel) userService.updateUser(pas, "ADMIN");
            }
            else{
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
            }

        }

    }

    @PostMapping(value = "/user/staff/update")
    private StaffModel updateStaff(@Valid @RequestBody RegisterKaryawanRequest pas, BindingResult bindingResult, HttpServletRequest request) {


        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
            if(userService.getCredential(request).getRole().equals("ADMIN") || userService.getCredential(request).getRole().equals("MANAJER")){
                return (StaffModel) userService.updateUser(pas, "STAFF");
            }
            else{
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
            }

        }

    }

    @PostMapping(value = "/user/manajer/update")
    private ManajerModel updateManajer(@Valid @RequestBody RegisterKaryawanRequest pas, BindingResult bindingResult, HttpServletRequest request) {


        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
            if(userService.getCredential(request).getRole().equals("ADMIN") || userService.getCredential(request).getRole().equals("MANAJER")){
                return (ManajerModel) userService.updateUser(pas, "MANAJER");
            }
            else{
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
            }

        }

    }

    @PostMapping(value = "/user/surveyor/update")
    private SurveyorModel updateSurveyor(@Valid @RequestBody RegisterKaryawanRequest pas, BindingResult bindingResult, HttpServletRequest request) {



        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
            if(userService.getCredential(request).getRole().equals("ADMIN") || userService.getCredential(request).getRole().equals("MANAJER")){
                return (SurveyorModel) userService.updateUser(pas, "SURVEYOR");
            }
            else{
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
            }

        }

    }

    @PostMapping(value = "/user/anggota/update")
    private AnggotaModel updateAnggota(@Valid @RequestBody AnggotaDTO pas, BindingResult bindingResult, HttpServletRequest request) {



        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "request body has invalid type or missing field");
        } else {
            if(userService.getCredential(request).getRole().equals("ADMIN") || userService.getCredential(request).getRole().equals("MANAJER")){
                return (AnggotaModel) userService.updateAnggota(pas);
            }
            else{
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Anda Bukan Admin");
            }

        }

    }


    @GetMapping("/user/admin/{username}")
    private AdminModel getAdmin(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @PathVariable("username") String username){

        if(
                userService.getCredential(authorizationHeader).getRole().equals("ADMIN")
                        || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")
                        || userService.getCredential(authorizationHeader).getUsername().equals(username)
        ){
            UserModel findUser = userService.getUserByUsername(username);

            if (findUser != null && findUser.getRole().equals("ADMIN")){
                return (AdminModel) findUser;
            }

            return null;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Tidak Dapat Akses Username ini");
        }

    }

    @GetMapping("/user/manajer/{username}")
    private ManajerModel getManajer(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @PathVariable("username") String username){

        if(
                userService.getCredential(authorizationHeader).getRole().equals("ADMIN")
                        || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")
                        || userService.getCredential(authorizationHeader).getUsername().equals(username)
        ){
            UserModel findUser = userService.getUserByUsername(username);

            if ((findUser != null) && findUser.getRole().equals("MANAJER")){
                return (ManajerModel) findUser;
            }

            return null;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Tidak Dapat Akses Username ini");
        }

    }


    @GetMapping("/user/surveyor/{username}")
    private SurveyorModel getSurveyor(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @PathVariable("username") String username){

        if(
                userService.getCredential(authorizationHeader).getRole().equals("ADMIN")
                        || userService.getCredential(authorizationHeader).getRole().equals("MANAJER")
                        || userService.getCredential(authorizationHeader).getUsername().equals(username)
        ){
            UserModel findUser = userService.getUserByUsername(username);

            if ((findUser != null) && findUser.getRole().equals("SURVEYOR")){
                return (SurveyorModel) findUser;
            }

            return null;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Anda Tidak Dapat Akses Username ini");

        }

    }
}
