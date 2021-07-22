package com.inclusiv.oio_project.repository;

import java.util.List;

import com.inclusiv.oio_project.model.MailNotif;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<MailNotif,Long>{

    @Query(value = "SELECT COUNT(`texte`) FROM `notification` WHERE `recu`=?1 OR `envoyer`=?2", nativeQuery = true)
    public Long countmessage(String recu,String envoyer);

    @Query(value = "SELECT COUNT(`notifadmin`) FROM `notification`", nativeQuery = true)
    public Long countmessageAdmin();

    @Query(value = "SELECT * FROM `notification` WHERE `recu`=?1 OR `envoyer`=?2 ORDER BY `id` DESC", nativeQuery = true)
    public List<MailNotif> rechercheNotification(String envoyer, String recu);
}
