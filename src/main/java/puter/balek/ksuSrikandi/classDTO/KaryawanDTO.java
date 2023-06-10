package puter.balek.ksuSrikandi.classDTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KaryawanDTO {

    private Long id;
    private String name;
    private String username;
    private String nik;
    private String role;
    private String tempatTanggalLahir;
    private String password;
    private String noPegawai;
}
