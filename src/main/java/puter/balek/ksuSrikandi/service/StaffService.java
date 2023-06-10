package puter.balek.ksuSrikandi.service;

import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.ManajerModel;
import puter.balek.ksuSrikandi.model.StaffModel;

import java.util.List;

@Service
public interface StaffService {

    List<StaffModel> getAllStaff();

    StaffModel getStaffById(Long id);
}
