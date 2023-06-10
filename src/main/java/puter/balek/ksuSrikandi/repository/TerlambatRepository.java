package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import puter.balek.ksuSrikandi.model.TerlambatModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Month;

public interface TerlambatRepository extends JpaRepository<TerlambatModel, Long> {
    TerlambatModel findByBulan(@NotNull String Month);
}
