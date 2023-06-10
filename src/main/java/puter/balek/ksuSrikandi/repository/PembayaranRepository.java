package puter.balek.ksuSrikandi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import puter.balek.ksuSrikandi.model.PembayaranModel;

import java.util.List;

@Repository
public interface PembayaranRepository extends JpaRepository<PembayaranModel, Long> {
    // Please define method if needed. Empty is fine too. You can still do basic CRUD
    @Query("SELECT c FROM PembayaranModel c WHERE c.pinjaman.id = :id")
    List<PembayaranModel> findAllByPinjamanId(@Param("id") Long id);
}
