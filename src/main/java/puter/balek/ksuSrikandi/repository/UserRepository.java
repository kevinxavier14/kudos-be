package puter.balek.ksuSrikandi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import puter.balek.ksuSrikandi.model.UserModel;

import java.util.List;
import java.util.Optional;

// This code is just for db testing purpose. Please re-code to the proper one.
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {



    @Query ("SELECT u FROM StaffModel u \n" +
            "WHERE u.noPegawai = :noPegawai \n" +
            "OR u.noPegawai = (SELECT a.username FROM AdminModel a WHERE a.noPegawai = :noPegawai) \n" +
            "OR u.noPegawai = (SELECT s.username FROM SurveyorModel s WHERE s.noPegawai = :noPegawai) \n" +
            "OR u.noPegawai = (SELECT m.username FROM ManajerModel m WHERE m.noPegawai = :noPegawai)\n")
    UserModel findBynoKaryawan(@Param("noPegawai") String noPegawai);
}
