package puter.balek.ksuSrikandi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "admin")
public class AdminModel extends UserModel implements Serializable {

    @NotNull
    @Column(name = "noPegawai", nullable = false)
    private String noPegawai;
}
