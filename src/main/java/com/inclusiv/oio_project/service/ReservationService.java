package com.inclusiv.oio_project.service;

import java.util.List;

import com.inclusiv.oio_project.model.Reservation;
import com.inclusiv.oio_project.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findCalendrierReservation(String nomprof, String prenomprof){
        return reservationRepository.findAllByNameComplete(nomprof, prenomprof);
    }

    public Reservation getByID(Long id) {
        return reservationRepository.findById(id).get();
    }

    public void updateReservationAdmin(String statutreservation, String validationadmin, Long id) {
        reservationRepository.updateReservationAdmin(statutreservation, validationadmin, id);
    }

    public List<Reservation> listeReservations(){
        return reservationRepository.findAll();        
    }

    public void uptadeValidationAdmin(String valide, Long id){
        reservationRepository.updateValidation(valide, id);
    }

    public void saveReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }

    public List<Reservation> reservationsStatut(String nom,String prenom,String statut,String valide){
        return reservationRepository.rechercheParStatut(nom, prenom, statut, valide);
    }

    public void supprimerReservation(Long id){
        reservationRepository.deleteById(id);
    }

    public void ajouterReservationParent(String statutreservation, String prenomparents, String nomparents,Long id){
        reservationRepository.updateReservationParent(statutreservation, prenomparents, nomparents, id);
    }

    public Reservation findReservationById(Long id){
        return reservationRepository.findReservationById(id);
    }

    public List<Reservation> rechercheReservationParent(String nom,String prenom){
        return reservationRepository.rechercheReservationParent(nom, prenom);
    }

    public List<Reservation> rechercheReservationFormateur(String nom,String prenom){
        return reservationRepository.rechercheReservationFormateur(nom, prenom);
    }

    public void updateTerminer(String est_terminer, Long id){
        reservationRepository.updateTerminer(est_terminer, id);
    }

    public void updateAnnuler(String statutreservation, String validationadmin,String nomparents,String prenomparents,Long id){
        reservationRepository.updateAnnuler(statutreservation, validationadmin, nomparents, prenomparents, id);
    }

    public void deleteReservation(Long id){
        reservationRepository.deleteById(id);
    }

}
