package puter.balek.ksuSrikandi.service;

import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.AdminModel;

import java.util.List;

@Service
public interface AdminService {

    List<AdminModel> getAllAdmin();

    AdminModel getAdminById(Long id);
}
