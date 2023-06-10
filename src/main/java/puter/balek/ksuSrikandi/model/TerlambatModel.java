package puter.balek.ksuSrikandi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;

@Data
@Entity
@Table(name = "terlambat")
public class TerlambatModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bulan", unique = true)
    @NotNull
    private String bulan;

    @Column(name = "julahTerlambat")
    private Long julahTerlambat;
}
