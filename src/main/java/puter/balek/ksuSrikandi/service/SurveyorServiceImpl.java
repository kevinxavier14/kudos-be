package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.SurveyorModel;
import puter.balek.ksuSrikandi.repository.SurveyorRepository;

import java.util.List;

@Service
public class SurveyorServiceImpl implements SurveyorService{

    @Autowired
    SurveyorRepository surveyorRepository;
    @Override
    public List<SurveyorModel> getAllSurveyor() {
        return surveyorRepository.findAll();
    }

    @Override
    public SurveyorModel getSurveyorById(Long id) {
        if (surveyorRepository.findById(id).get() != null){
            return surveyorRepository.findById(id).get();
        }else{
            return null;
        }
    }
}
