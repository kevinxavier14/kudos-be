package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.configJWT.RegisterKaryawanRequest;
import puter.balek.ksuSrikandi.model.AnggotaDTO;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.UserModel;
import puter.balek.ksuSrikandi.repository.AnggotaRepository;
import puter.balek.ksuSrikandi.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
// This code is just for db testing purpose. Please re-code UserModel.java to the proper one.
@Service
public interface UserService {

    UserModel saveUser(UserModel user) ;

    UserModel getUserById(Long id);

    void deleteUserById(Long id);

    UserModel getUserByUsername(String username);

    UserModel addUser(RegisterKaryawanRequest registerKaryawanRequest, String role);
    public String encrypt(String password);

    UserModel getCredential(HttpServletRequest request);

    UserModel getCredential(String request);

    String createNomorPegawai(RegisterKaryawanRequest registerKaryawanRequest);

    UserModel updateUser(RegisterKaryawanRequest registerKaryawanRequest, String role);

    UserModel deleteUser(String role, Long id);

    List<AnggotaModel> getAllAnggota();

    AnggotaModel addAnggota(AnggotaDTO anggotaDTO);

    UserModel updatePassword(String username, String oldPass, String newPass, String newPassConfirm);
    AnggotaModel getAnggotaByUsername(String username);

    AnggotaModel updateAnggota(AnggotaDTO anggotaDTO);


}
