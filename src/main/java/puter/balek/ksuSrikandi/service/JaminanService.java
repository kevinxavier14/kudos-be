package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.JaminanModel;
import puter.balek.ksuSrikandi.repository.JaminanRepository;

@Service
public class JaminanService {
    @Autowired
    private JaminanRepository jaminanRepository;

    public JaminanModel saveJaminan(JaminanModel jaminan){
        return jaminanRepository.save(jaminan);
    }

    public JaminanModel getJaminanById(Long jaminanId){
        return jaminanRepository.findById(jaminanId).orElse(null);
    }

    public void deleteJaminanById(Long jaminanId){
        jaminanRepository.deleteById(jaminanId);
    }
}
