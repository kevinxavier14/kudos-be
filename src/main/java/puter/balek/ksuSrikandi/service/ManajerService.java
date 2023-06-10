package puter.balek.ksuSrikandi.service;

import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.AdminModel;
import puter.balek.ksuSrikandi.model.ManajerModel;

import java.util.List;

@Service
public interface ManajerService {

    List<ManajerModel> getAllManajer();

    ManajerModel getManajerById(Long id);

}
