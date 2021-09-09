package com.inclusiv.oio_project.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.inclusiv.oio_project.model.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Query(value = "SELECT role FROM utilisateur WHERE email=1?", nativeQuery = true)
    public String findRoleByEmail(String email);

    @Query("select p from Utilisateur p where p.email = ?1 and p.password = ?2")
    public Utilisateur auth(String mail, String password);

    @Query("select p from Utilisateur p where p.email = ?1")
    public Utilisateur findByEmail(String mail);

    @Query("select p from Utilisateur p where p.id = ?1")
    public Utilisateur findUtilisateurByID(Long id);

    @Query(value = "SELECT COUNT(id) FROM utilisateur", nativeQuery = true)
    public Long countUtilisateur();

    @Query("select p from Utilisateur p where p.role = ?1")
    public List<Utilisateur> findListUtilisaterByRole(String role);

    @Transactional
    @Modifying
    @Query(value = "UPDATE utilisateur SET description=?1,email=?2,experience=?3,matiere=?4,niveau=?5,nom=?6,pays=?7,prenom=?8,tarif=?9,telephone=?10,ville=?11 WHERE id=?12", nativeQuery = true)
    public void updateprofilprof(String description, String email, int experience, String matiere, String niveau,
            String nom, String pays, String prenom, Double tarif, String telephone, String ville, Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE utilisateur SET nom=?1,prenom=?2,pays=?3,ville=?4,telephone=?5,email=?6,nbrenfant=?7,description=?8 WHERE id=?9", nativeQuery = true)
    public void updateprofilparent(String nom, String prenom, String pays, String ville, String telephone, String email,
            int nbrenfant, String description, Long id);

    @Query(value = "SELECT nom, prenom, tarif, date_inscription FROM utilisateur where role=?1 and year(date_inscription)=year(now()) and month(date_inscription)=month(now()) ", nativeQuery = true)
    public List<Utilisateur> findListUtilisaterByRoleMois(String role);

    @Query(value = "SELECT COUNT(role) FROM `utilisateur` WHERE role=?1", nativeQuery = true)
    public Long compteByRole(String role);

    @Query(value = "SELECT COUNT(distinct matiere) FROM `utilisateur`", nativeQuery = true)
    public Long compteToutMatiere();

    @Query(value = "select * from utilisateur where nom = ?1 and prenom = ?2", nativeQuery = true)
    public Utilisateur rechercheparentReservation(String nom, String prenom);

    @Query(value = "SELECT email FROM utilisateur WHERE nom=?1 AND prenom=?2", nativeQuery = true)
    public String rechercheemail(String nom , String prenom);
}
