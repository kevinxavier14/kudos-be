package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.ManajerModel;
import puter.balek.ksuSrikandi.repository.ManajerRepository;

import java.util.List;

@Service
public class ManajerServiceImpl implements  ManajerService{

    @Autowired
    ManajerRepository manajerRepository;

    @Override
    public List<ManajerModel> getAllManajer() {
        return manajerRepository.findAll();
    }

    @Override
    public ManajerModel getManajerById(Long id) {
        if (manajerRepository.findById(id).get() != null){
            return manajerRepository.findById(id).get();
        }else{
            return null;
        }
    }
}
