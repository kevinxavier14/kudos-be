package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puter.balek.ksuSrikandi.model.AdminModel;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Long> {

    @Query(" select a from AdminModel a Where a.username = :username")
    AdminModel findByUsername(String username);
}
