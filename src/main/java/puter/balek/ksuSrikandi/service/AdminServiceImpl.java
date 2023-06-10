package puter.balek.ksuSrikandi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puter.balek.ksuSrikandi.model.AdminModel;
import puter.balek.ksuSrikandi.repository.AdminRepository;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminRepository adminRepository;


    @Override
    public List<AdminModel> getAllAdmin() {

        List<AdminModel> adminModelList = adminRepository.findAll();

        return adminModelList;
    }

    @Override
    public AdminModel getAdminById(Long id) {

        if (adminRepository.findById(id).get() != null){
            return adminRepository.findById(id).get();
        }else{
            return null;
        }
    }
}
