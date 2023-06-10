package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import puter.balek.ksuSrikandi.model.PengajuanAnggotaModel;

@Repository
public interface PengajuanAnggotaRepository extends JpaRepository<PengajuanAnggotaModel, Long> {
    // Please define method if needed. Empty is fine too. You can still do basic CRUD
    @Query("select a from PengajuanAnggotaModel a Where a.username = :username")
    PengajuanAnggotaModel findByUsername(String username);
}
