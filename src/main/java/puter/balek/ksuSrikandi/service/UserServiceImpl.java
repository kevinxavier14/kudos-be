package puter.balek.ksuSrikandi.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.configJWT.JwtTokenUtil;
import puter.balek.ksuSrikandi.configJWT.RegisterKaryawanRequest;
import puter.balek.ksuSrikandi.model.*;
import puter.balek.ksuSrikandi.model.AnggotaDTO;
import puter.balek.ksuSrikandi.repository.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ManajerRepository manajerRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    SurveyorRepository surveyorRepository;

    @Autowired
    AnggotaRepository anggotaRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @Override
    public UserModel saveUser(UserModel user) {
        return null;
    }

    @Override
    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public UserModel getUserByUsername(String username) {

        if (adminRepository.findByUsername(username) != null){
            return (adminRepository.findByUsername(username));
        }else if ((manajerRepository.findByUsername(username) != null)){
            return (manajerRepository.findByUsername(username));

        }else if (surveyorRepository.findByUsername(username) != null){
            return (surveyorRepository.findByUsername(username) );

        }else if(anggotaRepository.findByUsername(username) != null) {
            return (anggotaRepository.findByUsername(username));
        }
        else {
            return (staffRepository.findByUsername(username));
        }
    }

    @Override
    public AnggotaModel getAnggotaByUsername(String username) {
        System.out.println(anggotaRepository.findByUsername(username));
        return anggotaRepository.findByUsername(username);
    }

    @Override
    public AnggotaModel updateAnggota(AnggotaDTO anggotaDTO) {
        AnggotaModel anggotaModel = anggotaRepository.findByUsername(anggotaDTO.getUsername());

        anggotaModel.setUsername(anggotaDTO.getUsername());
        anggotaModel.setNik(anggotaDTO.getNik());
        anggotaModel.setTempatTanggalLahir(anggotaDTO.getTempatTanggalLahir());
        String newNoAnggota = anggotaDTO.getNoAnggota() + ".1";
        anggotaModel.setNoAnggota(newNoAnggota);
        anggotaModel.setName(anggotaDTO.getName());

        anggotaModel.setTanggalBerlaku(LocalDate.of(2020, 1, 8));
        anggotaModel.setAgama(anggotaDTO.getAgama());
        anggotaModel.setAlamat(anggotaDTO.getAlamat());
        anggotaModel.setAlamatAhliWaris(anggotaDTO.getAlamatAhliWaris());
        anggotaModel.setNamaAhliWaris(anggotaDTO.getNamaAhliWaris());
        anggotaModel.setNamaIbu(anggotaDTO.getNamaIbu());
        anggotaModel.setNoTelpon(anggotaDTO.getNoTelpon());
        anggotaModel.setPekerjaan(anggotaDTO.getPekerjaan());
        anggotaModel.setStatusMartial(anggotaDTO.getStatusMartial());
        anggotaModel.setNoTelponAhliWaris(anggotaDTO.getNoTelponAhliWaris());

        anggotaModel.setRole("ANGGOTA");

        anggotaRepository.save(anggotaModel);


        return anggotaModel;
    }

    @Override

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public UserModel getCredential(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }

        UserModel ret = this.getUserByUsername(jwtTokenUtil.getUsernameFromToken(jwtToken));

        return ret;
    }

    @Override
    public UserModel getCredential(String authorizationHeader) {
        String jwtToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }

        UserModel ret = this.getUserByUsername(jwtTokenUtil.getUsernameFromToken(jwtToken));

        return ret;
    }

    @Override
    public String createNomorPegawai(RegisterKaryawanRequest registerKaryawanRequest) {


        String noPegawai = registerKaryawanRequest.getName().substring(0, 4);
        String arrTgl = registerKaryawanRequest.getTanggalLahir();

        noPegawai += arrTgl;

        int counter = 1;

        while (true){
            noPegawai += Integer.toString(counter);

            if (userRepository.findBynoKaryawan(noPegawai) != null){
                counter ++;
            }else{
                break;
            }

        }

        return noPegawai;

    }

    @Override
    public UserModel updateUser(RegisterKaryawanRequest registerKaryawanRequest, String role) {
        String tempatTanggalLahir = registerKaryawanRequest.getTempatTanggalLahir();
        String pass = null;
        System.out.println(registerKaryawanRequest.getUsername());
        if (registerKaryawanRequest.getPassword() != null){
            pass = this.encrypt(registerKaryawanRequest.getPassword());
        }


        if (role.equals("MANAJER")){

            //role if it's the manager
            ManajerModel newKaryawanUser = (ManajerModel) this.getUserByUsername(registerKaryawanRequest.getUsername());
            System.out.println(newKaryawanUser);

            newKaryawanUser.setNik(registerKaryawanRequest.getNik());
            newKaryawanUser.setName(registerKaryawanRequest.getName());
            newKaryawanUser.setUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setTempatTanggalLahir(tempatTanggalLahir);
//            newKaryawanUser.setRole(registerKaryawanRequest.getRole());
            if (pass != null){
                newKaryawanUser.setPassword(pass);
            }


            return manajerRepository.save(newKaryawanUser);

        }else if (role.equals("STAFF")){
            //role if it's the manager
            StaffModel newKaryawanUser = (StaffModel) this.getUserByUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setNik(registerKaryawanRequest.getNik());
            newKaryawanUser.setName(registerKaryawanRequest.getName());
            newKaryawanUser.setUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setTempatTanggalLahir(tempatTanggalLahir);
//            newKaryawanUser.setRole(registerKaryawanRequest.getRole());
            if (pass != null){
                newKaryawanUser.setPassword(pass);
            }

            return staffRepository.save(newKaryawanUser);

        }else if (role.equals("SURVEYOR")){
            //role if it's the manager
            SurveyorModel newKaryawanUser = (SurveyorModel) this.getUserByUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setNik(registerKaryawanRequest.getNik());
            newKaryawanUser.setName(registerKaryawanRequest.getName());
            newKaryawanUser.setUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setTempatTanggalLahir(tempatTanggalLahir);
//            newKaryawanUser.setRole(registerKaryawanRequest.getRole());
            if (pass != null){
                newKaryawanUser.setPassword(pass);
            }

            return surveyorRepository.save(newKaryawanUser);

        }else if (role.equals("ADMIN")){
            //role if it's the manager
            AdminModel newKaryawanUser = (AdminModel) this.getUserByUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setNik(registerKaryawanRequest.getNik());
            newKaryawanUser.setName(registerKaryawanRequest.getName());
            newKaryawanUser.setUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setTempatTanggalLahir(tempatTanggalLahir);
//            newKaryawanUser.setRole(registerKaryawanRequest.getRole());
            if (pass != null){
                newKaryawanUser.setPassword(pass);
            }
            System.out.println("HERE");

            return adminRepository.save(newKaryawanUser);

        }else{
            return null;
        }
    }

    @Override
    public UserModel deleteUser(String role, Long id) {

        if (role.toUpperCase().equals("MANAJER")){
            ManajerModel returnModel = manajerRepository.findById(id).get();
            manajerRepository.deleteById(id);
            return returnModel;
        }else if (role.toUpperCase().equals("STAFF")){
            StaffModel returnModel = staffRepository.findById(id).get();
            staffRepository.deleteById(id);
            return returnModel;
        }else if (role.toUpperCase().equals("SURVEYOR")){
            SurveyorModel returnModel = surveyorRepository.findById(id).get();
            surveyorRepository.deleteById(id);
            return returnModel;
        }else if (role.toUpperCase().equals("ANGGOTA")){
            AnggotaModel returnModel = anggotaRepository.findById(id).get();
            anggotaRepository.deleteById(id);
            return returnModel;

        }


        return null;
    }

    @Override
    public List<AnggotaModel> getAllAnggota() {
        return anggotaRepository.findAll();
    }

    @Override
    public AnggotaModel addAnggota(AnggotaDTO anggotaDTO) {

        AnggotaModel anggotaModel = new AnggotaModel();
        System.out.println("JPN"+ anggotaDTO.getUsername()+"PAN");
        System.out.println("JPN"+ anggotaDTO.getPassword()+"PAN");

        anggotaModel.setPassword(encrypt(anggotaDTO.getPassword()));
        anggotaModel.setUsername(anggotaDTO.getUsername());
        anggotaModel.setNik(anggotaDTO.getNik());
        anggotaModel.setTempatTanggalLahir(anggotaDTO.getTempatTanggalLahir());
        anggotaModel.setNoAnggota("238921389482");
        anggotaModel.setName(anggotaDTO.getName());

        anggotaModel.setTanggalBerlaku(LocalDate.of(2020, 1, 8));
        anggotaModel.setAgama(anggotaDTO.getAgama());
        anggotaModel.setAlamat(anggotaDTO.getAlamat());
        anggotaModel.setAlamatAhliWaris(anggotaDTO.getAlamatAhliWaris());
        anggotaModel.setNamaAhliWaris(anggotaDTO.getNamaAhliWaris());
        anggotaModel.setNamaIbu(anggotaDTO.getNamaIbu());
        anggotaModel.setNoTelpon(anggotaDTO.getNoTelpon());
        anggotaModel.setPekerjaan(anggotaDTO.getPekerjaan());
        anggotaModel.setStatusMartial(anggotaDTO.getStatusMartial());
        anggotaModel.setNoTelponAhliWaris(anggotaDTO.getNoTelponAhliWaris());

        anggotaModel.setRole("ANGGOTA");

        anggotaRepository.save(anggotaModel);

        return anggotaModel;
    }

    @Override
    public UserModel updatePassword(String username, String oldPass, String newPass, String newPassConfirm) {
        return null;
    }


    @Override
    public UserModel addUser(RegisterKaryawanRequest registerKaryawanRequest, String role) {


        String tempatTanggalLahir = registerKaryawanRequest.getTempatLahir() + ", " + registerKaryawanRequest.getTanggalLahir();
        String pass = this.encrypt(registerKaryawanRequest.getPassword());

        String noPegawai = this.createNomorPegawai(registerKaryawanRequest);

        if (role.equals("MANAJER")){

            //role if it's the manager
            ManajerModel newKaryawanUser = new ManajerModel();
            newKaryawanUser.setRole("MANAJER");
            newKaryawanUser.setNik(registerKaryawanRequest.getNik());
            newKaryawanUser.setName(registerKaryawanRequest.getName());

            newKaryawanUser.setUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setNoPegawai(noPegawai);
            newKaryawanUser.setPassword(pass);
            newKaryawanUser.setTempatTanggalLahir(tempatTanggalLahir);

            return manajerRepository.save(newKaryawanUser);

        }else if (role.equals("STAFF")){
            //role if it's the manager
            StaffModel newKaryawanUser = new StaffModel();
            newKaryawanUser.setRole("STAFF");
            newKaryawanUser.setNik(registerKaryawanRequest.getNik());
            newKaryawanUser.setName(registerKaryawanRequest.getName());
            newKaryawanUser.setUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setNoPegawai(noPegawai);
            newKaryawanUser.setPassword(pass);
            newKaryawanUser.setTempatTanggalLahir(tempatTanggalLahir);

            return staffRepository.save(newKaryawanUser);

        }else if (role.equals("SURVEYOR")){
            //role if it's the manager
            SurveyorModel newKaryawanUser = new SurveyorModel();
            newKaryawanUser.setRole("SURVEYOR");
            newKaryawanUser.setNik(registerKaryawanRequest.getNik());
            newKaryawanUser.setName(registerKaryawanRequest.getName());
            newKaryawanUser.setUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setNoPegawai(noPegawai);
            newKaryawanUser.setPassword(pass);
            newKaryawanUser.setTempatTanggalLahir(tempatTanggalLahir);

            return surveyorRepository.save(newKaryawanUser);

        }else if (role.equals("ADMIN")){
            //role if it's the manager
            AdminModel newKaryawanUser = new AdminModel();
            newKaryawanUser.setRole("ADMIN");
            newKaryawanUser.setNik(registerKaryawanRequest.getNik());
            newKaryawanUser.setName(registerKaryawanRequest.getName());
            newKaryawanUser.setUsername(registerKaryawanRequest.getUsername());
            newKaryawanUser.setNoPegawai(noPegawai);
            newKaryawanUser.setPassword(pass);
            newKaryawanUser.setTempatTanggalLahir(tempatTanggalLahir);

            return adminRepository.save(newKaryawanUser);

        }else{
            return null;
        }
    }
}
