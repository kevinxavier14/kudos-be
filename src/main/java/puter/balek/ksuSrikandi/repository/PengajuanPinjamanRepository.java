package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import puter.balek.ksuSrikandi.model.PengajuanPinjamanModel;

import java.util.List;
import java.util.Optional;

public interface PengajuanPinjamanRepository extends JpaRepository<PengajuanPinjamanModel, Long> {
    // Please define method if needed. Empty is fine too. You can still do basic CRUD
    // JPA
    @Override
    Optional<PengajuanPinjamanModel> findById(Long id);

    @Query("SELECT c FROM PengajuanPinjamanModel c WHERE c.anggota.id = :id")
    List<PengajuanPinjamanModel> findAllByAnggotaId(@Param("id") Long id);
}
