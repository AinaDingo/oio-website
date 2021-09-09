package com.inclusiv.oio_project.service;

import java.util.List;

import com.inclusiv.oio_project.model.MailNotif;
import com.inclusiv.oio_project.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;


    public void saveNotif(MailNotif notif){
        notificationRepository.save(notif);
    }

    public List<MailNotif> rechercheMessageParRecu(String recu){
        return null;

    }

    public List<MailNotif> recherchemessageParEnvoyer(String envoyer){
        return null;
    }

    public Long countMessage(String recu, String envoyer){
        return notificationRepository.countmessage(recu, envoyer);
    }

    public List<MailNotif> rechercheNotification(String envoyer, String recu){
        return notificationRepository.rechercheNotification(envoyer, recu);
    }

    public List<MailNotif> rechercheNotifAdmin(){
        return notificationRepository.findAll();
    }

    public Long countmessageAdmin(){
        return notificationRepository.countmessageAdmin();
    }
}
