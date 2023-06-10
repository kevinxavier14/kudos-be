package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puter.balek.ksuSrikandi.model.AdminModel;
import puter.balek.ksuSrikandi.model.ManajerModel;

@Repository
public interface ManajerRepository extends JpaRepository<ManajerModel, Long> {

    @Query(" select a from ManajerModel a Where a.username = :username")
    ManajerModel findByUsername(String username);
}
