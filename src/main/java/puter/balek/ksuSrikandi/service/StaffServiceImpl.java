package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.StaffModel;
import puter.balek.ksuSrikandi.repository.StaffRepository;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService{

    @Autowired
    StaffRepository staffRepository;

    @Override
    public List<StaffModel> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public StaffModel getStaffById(Long id) {
        if (staffRepository.findById(id).get() != null){
            return staffRepository.findById(id).get();
        }else{
            return null;
        }
    }
}
