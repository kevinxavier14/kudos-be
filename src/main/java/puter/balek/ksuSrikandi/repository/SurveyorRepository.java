package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import puter.balek.ksuSrikandi.model.AdminModel;
import puter.balek.ksuSrikandi.model.SurveyorModel;

@Repository
public interface SurveyorRepository extends JpaRepository<SurveyorModel, Long> {
    SurveyorModel findByUsername(String username);
}
