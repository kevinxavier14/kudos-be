package puter.balek.ksuSrikandi.configJWT;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterKaryawanRequest {


    private String name;

    private String username;
    private String nik;
    private String password;
    private String role;
    private String tempatLahir;
    private String tanggalLahir;
    private String noPegawai;
    private String tempatTanggalLahir;


    public RegisterKaryawanRequest() {
    }


}
