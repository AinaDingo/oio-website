package com.inclusiv.oio_project.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  length = 50)
    private String nom;

    @Column(nullable = false,  length = 50)
    private String prenom;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false,  length = 50)
    private String role;

    @Column(nullable = true,  length = 50)
    private String telephone;
     
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    
    @Column(nullable = false,  length = 50)
    private String pays;

    @Column(nullable = false,  length = 50)
    private String ville;

    @Lob
    @Column(nullable = false)
    private String pdp;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private Date date_inscription;

    @Column(nullable = true, length = 10)
    private int experience;

    @Lob
    @Column(nullable = true)
    private String documents;

    @Column(nullable = true, length = 10)
    private Double tarif;

    @Column(nullable = true, length = 50)
    private String matiere;

    @Column(nullable = true, length = 50)
    private String niveau;

    @Column(nullable = true, length = 10)
    private int nbrenfant;

    public Utilisateur() {
    }

    public Utilisateur(Long id, String nom, String prenom, String password, String role, String telephone, String email,
            String pays, String ville, String pdp, String description, Date date_inscription, int experience,
            String documents, Double tarif, String matiere, String niveau, int nbrenfant) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.role = role;
        this.telephone = telephone;
        this.email = email;
        this.pays = pays;
        this.ville = ville;
        this.pdp = pdp;
        this.description = description;
        this.date_inscription = date_inscription;
        this.experience = experience;
        this.documents = documents;
        this.tarif = tarif;
        this.matiere = matiere;
        this.niveau = niveau;
        this.nbrenfant = nbrenfant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPdp() {
        return pdp;
    }

    public void setPdp(String pdp) {
        this.pdp = pdp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getNbrenfant() {
        return nbrenfant;
    }

    public void setNbrenfant(int nbrenfant) {
        this.nbrenfant = nbrenfant;
    }

    
}
