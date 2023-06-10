package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import puter.balek.ksuSrikandi.model.AdminModel;
import puter.balek.ksuSrikandi.model.StaffModel;

@Repository
public interface StaffRepository extends JpaRepository<StaffModel, Long> {

    StaffModel findByUsername(String username);
}
