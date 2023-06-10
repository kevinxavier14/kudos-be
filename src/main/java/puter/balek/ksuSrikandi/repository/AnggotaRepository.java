package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puter.balek.ksuSrikandi.model.AnggotaModel;
import puter.balek.ksuSrikandi.model.ManajerModel;
import puter.balek.ksuSrikandi.model.AdminModel;

@Repository
public interface AnggotaRepository extends JpaRepository<AnggotaModel, Long> {
    @Query(" select a from AnggotaModel a Where a.username = :username")
    AnggotaModel findByUsername(String username);

}
