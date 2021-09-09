package com.inclusiv.oio_project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class MailNotif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 255)
    private String texte;

    @Column(nullable = true, length = 50)
    private String recu;

    @Column(nullable = true, length = 50)
    private String envoyer;

    @Column(nullable = true, length = 255)
    public String notifadmin;

    public MailNotif() {
    }

    public MailNotif(Long id, String texte, String recu, String envoyer, String notifadmin) {
        this.id = id;
        this.texte = texte;
        this.recu = recu;
        this.envoyer = envoyer;
        this.notifadmin = notifadmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getRecu() {
        return recu;
    }

    public void setRecu(String recu) {
        this.recu = recu;
    }

    public String getEnvoyer() {
        return envoyer;
    }

    public void setEnvoyer(String envoyer) {
        this.envoyer = envoyer;
    }

    public String getnotifadmin() {
        return notifadmin;
    }

    public void setnotifadmin(String notifadmin) {
        this.notifadmin = notifadmin;
    }

}
