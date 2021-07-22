package com.inclusiv.oio_project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date datereservation;

    @Column(nullable = false)
    private Date datecours;

    @Column(nullable = false)
    private Time heuredebut;

    @Column(nullable = false)
    private Time heurefin;

    @Column(nullable = true, length = 50)
    private String nomparents;

    @Column(nullable = true, length = 50)
    private String prenomparents;

    @Column(nullable = false, length = 50)
    private String nomprof;

    @Column(nullable = false, length = 50)
    private String prenomprof;

    @Column(nullable = true, length = 50)
    private String matiere;

    @Column(nullable = true, length = 50)
    private String niveau;

    @Column(nullable = true, length = 50)
    private String statutreservation;

    @Column(nullable = true, length = 50)
    private String validationadmin;

    @Column(nullable = true, length = 50)
    private String estTerminer;

    public Reservation() {
    }

    public Reservation(Long id, Date datereservation, Date datecours, Time heuredebut, Time heurefin, String nomparents,
            String prenomparents, String nomprof, String prenomprof, String matiere, String niveau,
            String statutreservation, String validationadmin, String estTerminer) {
        this.id = id;
        this.datereservation = datereservation;
        this.datecours = datecours;
        this.heuredebut = heuredebut;
        this.heurefin = heurefin;
        this.nomparents = nomparents;
        this.prenomparents = prenomparents;
        this.nomprof = nomprof;
        this.prenomprof = prenomprof;
        this.matiere = matiere;
        this.niveau = niveau;
        this.statutreservation = statutreservation;
        this.validationadmin = validationadmin;
        this.estTerminer = estTerminer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatereservation() {
        return datereservation;
    }

    public void setDatereservation(Date datereservation) {
        this.datereservation = datereservation;
    }

    public Date getDatecours() {
        return datecours;
    }

    public void setDatecours(Date datecours) {
        this.datecours = datecours;
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

    public String getNomparents() {
        return nomparents;
    }

    public void setNomparents(String nomparents) {
        this.nomparents = nomparents;
    }

    public String getPrenomparents() {
        return prenomparents;
    }

    public void setPrenomparents(String prenomparents) {
        this.prenomparents = prenomparents;
    }

    public String getNomprof() {
        return nomprof;
    }

    public void setNomprof(String nomprof) {
        this.nomprof = nomprof;
    }

    public String getPrenomprof() {
        return prenomprof;
    }

    public void setPrenomprof(String prenomprof) {
        this.prenomprof = prenomprof;
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

    public String getStatutreservation() {
        return statutreservation;
    }

    public void setStatutreservation(String statutreservation) {
        this.statutreservation = statutreservation;
    }

    public String getValidationadmin() {
        return validationadmin;
    }

    public void setValidationadmin(String validationadmin) {
        this.validationadmin = validationadmin;
    }

    public String getEstTerminer() {
        return estTerminer;
    }

    public void setEstTerminer(String estTerminer) {
        this.estTerminer = estTerminer;
    }

    
}
