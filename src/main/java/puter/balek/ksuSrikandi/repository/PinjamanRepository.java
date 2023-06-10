package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puter.balek.ksuSrikandi.model.PinjamanModel;

import java.util.List;

public interface PinjamanRepository extends JpaRepository<PinjamanModel, Long> {
    // Please define method if needed. Empty is fine too. You can still do basic CRUD

    @Query(value = "SELECT p.* FROM pinjaman p INNER JOIN anggota a ON p.anggota_id = a.id WHERE p.status_pinjaman = :status AND p.anggota_id = :id", nativeQuery = true)
    List<PinjamanModel> findByAnggotaIdAndStatus(Long id, String status);


}
