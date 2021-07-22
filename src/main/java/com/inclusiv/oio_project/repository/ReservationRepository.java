package com.inclusiv.oio_project.repository;

import java.util.List;

import com.inclusiv.oio_project.model.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r where r.nomprof=?1 and r.prenomprof=?2 order by r.datecours")
    public List<Reservation> findAllByNameComplete(String nomprof, String prenomprof);

    @Transactional
    @Modifying
    @Query("update Reservation r set r.statutreservation = ?1, r.validationadmin = ?2 where r.id = ?3")
    public void updateReservationAdmin(String statut, String valide, Long id);

    @Transactional
    @Modifying
    @Query("update Reservation r set r.validationadmin = ?1 where r.id = ?2")
    public void updateValidation(String valide, Long id);
    

    @Query(value = "SELECT * FROM Reservation WHERE nomprof=?1 AND prenomprof=?2 AND statutreservation=?3 AND validationadmin=?4",nativeQuery = true)
    public List<Reservation> rechercheParStatut(String nom,String prenom,String statut,String valide);

    @Transactional
    @Modifying
    @Query("update Reservation r set r.statutreservation = ?1, r.prenomparents = ?2, r.nomparents = ?3 where r.id = ?4")
    public void updateReservationParent(String statutreservation, String prenomparents, String nomparents,Long id);

    @Query("SELECT r FROM Reservation r where r.id=?1")
    public Reservation findReservationById(Long id);

    @Query(value = "SELECT * FROM Reservation WHERE nomparents=?1 AND prenomparents=?2 AND validationadmin='Validé'",nativeQuery = true)
    public List<Reservation> rechercheReservationParent(String nom,String prenom);

    @Query(value = "SELECT * FROM Reservation WHERE nomprof=?1 AND prenomprof=?2 AND validationadmin='Validé'",nativeQuery = true)
    public List<Reservation> rechercheReservationFormateur(String nom,String prenom);

    @Transactional
    @Modifying
    @Query("update Reservation r SET r.estTerminer=?1 WHERE r.id=?2")
    public void updateTerminer(String est_terminer, Long id);

    @Transactional
    @Modifying
    @Query("update Reservation r SET r.statutreservation=?1,r.validationadmin=?2,r.nomparents=?3,r.prenomparents=?4 WHERE r.id=?5")
    public void updateAnnuler(String statutreservation, String validationadmin,String nomparents,String prenomparents,Long id);
}