package puter.balek.ksuSrikandi.service;

import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.SurveyorModel;

import java.util.List;

@Service
public interface SurveyorService {

    List<SurveyorModel> getAllSurveyor();

    SurveyorModel getSurveyorById(Long id);
}
