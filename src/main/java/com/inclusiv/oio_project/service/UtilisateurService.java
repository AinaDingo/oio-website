package com.inclusiv.oio_project.service;

import java.util.List;

import com.inclusiv.oio_project.model.Utilisateur;
import com.inclusiv.oio_project.repository.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public void saveUser(Utilisateur userOio) {
        utilisateurRepository.save(userOio);
    }

    public String findRole(String email) {
        return utilisateurRepository.findRoleByEmail(email);
    }

    public Utilisateur auth(String email, String password) {
        return utilisateurRepository.auth(email, password);
    }

    public Utilisateur findUser(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur findUtilisateurByID(Long id) {
        return utilisateurRepository.findUtilisateurByID(id);
    }

    public Long countUtilisateurByID() {
        return utilisateurRepository.countUtilisateur();
    }

    public List<Utilisateur> findListeUtilisateurByRole(String role) {
        return utilisateurRepository.findListUtilisaterByRole(role);
    }

    public void updateprofilprof(String description, String email, int experience, String matiere, String niveau,
            String nom, String pays, String prenom, Double tarif, String telephone, String ville, Long id) {
        utilisateurRepository.updateprofilprof(description, email, experience, matiere, niveau, nom, pays, prenom,
                tarif, telephone, ville, id);
    }

    public void updateprofilparent(String nom, String prenom, String pays, String ville, String telephone, String email,
            int nbrenfant, String description, Long id) {
        utilisateurRepository.updateprofilparent(nom, prenom, pays, ville, telephone, email, nbrenfant, description,
                id);
    }

    public Long countByRole(String role) {
        return utilisateurRepository.compteByRole(role);
    }

    public Long CountMatiere() {

        return utilisateurRepository.compteToutMatiere();
    }

    public List<Utilisateur> findListUtilisaterByRoleMois(String role) {
        return utilisateurRepository.findListUtilisaterByRole(role);
    }

    public void deleteProf(Long id){
        utilisateurRepository.deleteById(id);
    }

    public void deleteParent(Long id){
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur rechercheparentReservation(String nom, String prenom){
        return utilisateurRepository.rechercheparentReservation(nom, prenom);
    }

    public String rechercheemail(String nom, String prenom){
        return utilisateurRepository.rechercheemail(nom, prenom);
    }
}
