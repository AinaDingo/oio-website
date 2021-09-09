package com.inclusiv.oio_project.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paiement")
public class Paiement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date datepaiement;

    @Column(nullable = false)
    private Time heuredebut;

    @Column(nullable = false)
    private Time heurefin;

    @Column(nullable = false)
    private Double duree;

    @Column(nullable = false, length = 50)
    private String matiere;

    @Column(nullable = true, length = 50)
    private String niveau;

    @Column(nullable = false, length = 50)
    private String nomparent;

    @Column(nullable = false, length = 50)
    private String prenomparent;

    @Column(nullable = false, length = 50)
    private String nomformateur;

    @Column(nullable = false, length = 50)
    private String prenomformateur;

    @Column(nullable = false, length = 10)
    private Double tarif;

    @Column(nullable = false, length = 10)
    private Double total;

    public Paiement() {
    }

    public Paiement(Long id, Date datepaiement, Time heuredebut, Time heurefin, Double duree, String matiere,
            String niveau, String nomparent, String prenomparent, String nomformateur, String prenomformateur,
            Double tarif, Double total) {
        this.id = id;
        this.datepaiement = datepaiement;
        this.heuredebut = heuredebut;
        this.heurefin = heurefin;
        this.duree = duree;
        this.matiere = matiere;
        this.niveau = niveau;
        this.nomparent = nomparent;
        this.prenomparent = prenomparent;
        this.nomformateur = nomformateur;
        this.prenomformateur = prenomformateur;
        this.tarif = tarif;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatepaiement() {
        return datepaiement;
    }

    public void setDatepaiement(Date datepaiement) {
        this.datepaiement = datepaiement;
    }

    public Time getHeuredebut() {
        return heuredebut;
    }

    public void setHeuredebut(Time heuredebut) {
        this.heuredebut = heuredebut;
    }

    public Time getHeurefin() {
        return heurefin;
    }

    public void setHeurefin(Time heurefin) {
        this.heurefin = heurefin;
    }

    public Double getDuree() {
        return duree;
    }

    public void setDuree(Double duree) {
        this.duree = duree;
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

    public String getNomparent() {
        return nomparent;
    }

    public void setNomparent(String nomparent) {
        this.nomparent = nomparent;
    }

    public String getPrenomparent() {
        return prenomparent;
    }

    public void setPrenomparent(String prenomparent) {
        this.prenomparent = prenomparent;
    }

    public String getNomformateur() {
        return nomformateur;
    }

    public void setNomformateur(String nomformateur) {
        this.nomformateur = nomformateur;
    }

    public String getPrenomformateur() {
        return prenomformateur;
    }

    public void setPrenomformateur(String prenomformateur) {
        this.prenomformateur = prenomformateur;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    
}
