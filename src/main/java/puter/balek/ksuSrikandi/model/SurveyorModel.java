package puter.balek.ksuSrikandi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "surveyor")
public class SurveyorModel extends UserModel implements Serializable {

    @NotNull
    @Column(name = "noPegawai", nullable = false)
    private String noPegawai;

    @OneToMany(mappedBy = "surveyor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude  // Exclude listPengajuanPinjaman from toString()
    private List<PengajuanPinjamanModel> listPengajuanPinjaman;
}
