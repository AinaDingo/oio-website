package com.inclusiv.oio_project.controller;

import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;

import com.inclusiv.oio_project.model.MailNotif;
import com.inclusiv.oio_project.model.Reservation;
import com.inclusiv.oio_project.model.Utilisateur;
import com.inclusiv.oio_project.service.NotificationService;
import com.inclusiv.oio_project.service.ReservationService;
import com.inclusiv.oio_project.service.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class OioController {
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    public NotificationService notificationService;

    //PAGE D'ACCUEIL
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String vuePageOio(Model model) {
        model.addAttribute("listEns", utilisateurService.findListeUtilisateurByRole("Formateur"));
        model.addAttribute("compteFormateur", utilisateurService.countByRole("Formateur"));
        model.addAttribute("compteMatiere", utilisateurService.CountMatiere());
        model.addAttribute("compteParent", utilisateurService.countByRole("Parent"));
        return "oio";
    }

    //LOGIN
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String vuePageConnexion() {
        return "autentification";
    }

    //SIGNUP_VUE
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String vuePageInscription(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "inscription";
    }


    //SIGUP_POST
    @PostMapping("/process_register")
    public String addUser(@ModelAttribute("user") Utilisateur user, MultipartFile photo) {
        try {
            String encodedPassword;
            encodedPassword = HashPassword.encryptString(user.getPassword());
            user.setPassword(encodedPassword);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }

        String photo_name = StringUtils.cleanPath(photo.getOriginalFilename());

        if (photo_name.contains("..")) {
            System.out.println("Image non valide");
        }

        try {
            user.setPdp(Base64.getEncoder().encodeToString(photo.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date date = new Date(System.currentTimeMillis());
        user.setDate_inscription(date);

        utilisateurService.saveUser(user);
        return "redirect:/";
    }

    // MOT DE PASSE OUBLIE
    @RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
    public String vuePageMotDePasseOublie() {
        return "mdpoublie";
    }

    //CHANGER MOT DE PASSE
    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public String vuePageChangerMotDePasse() {
        return "changermdp";
    }

    //PARRAINAGE
    @RequestMapping(value = "/parrainage", method = RequestMethod.GET)
    public String vuePageParrainage() {
        return "parrainage";
    }

    //AUTHENTIFICATION
    @RequestMapping(value = { "/auth" }, method = RequestMethod.POST)
    public String loginPage(@RequestParam("_email") String mail, @RequestParam("_mdp") String password)
            throws NoSuchAlgorithmException {
        String mdp = HashPassword.encryptString(password);
        Utilisateur u = utilisateurService.auth(mail, mdp);
        System.out.println(u.getRole());
        if (u.getRole().equals("Parent")) {
            return "redirect:/parent/" + u.getId();
        } else if (u.getRole().equals("Formateur")) {
            return "redirect:/formateur/" + u.getId();
        } else if (u.getRole().equals("Administrateur")) {
            return "redirect:/admin/" + u.getId();
        } else {
            return "redirect:/404";
        }
    }

    /*---------------404--------------*/
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String erreur404() {
        return "404";
    }

    /*---------------PARENT--------------*/

    // PARENT ACCUEIL
    @RequestMapping(value = "/parent/{id}", method = RequestMethod.GET)
    public String vueParent(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("parent", utilisateur);
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));
        return "parentTabDeBord";
    }

    // PARENT PROFIL
    @RequestMapping(value = "/parent/{id}/profil", method = RequestMethod.GET)
    public String vueParentProfil(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("parent", utilisateur);
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));
        return "parentProfil";
    }


    // PARENT LISTE
    @RequestMapping(value = "/parent/{id}/liste", method = RequestMethod.GET)
    public String vueParentListe(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("parent", utilisateur);
        model.addAttribute("listeProf", utilisateurService.findListeUtilisateurByRole("Formateur"));
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));
        return "parentListeEnseignants";
    }

    // PARENT RDV
    @RequestMapping(value = "/parent/{id}/rdv", method = RequestMethod.GET)
    public String vueParentRdv(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur parent = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("parent", parent);
        model.addAttribute("listreservation",
                reservationService.rechercheReservationParent(parent.getNom(), parent.getPrenom()));
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(parent.getEmail(), parent.getEmail()));
        model.addAttribute("countNotif", notificationService.countMessage(parent.getEmail(), parent.getEmail()));
        return "parentGestionRDV";
    }

    // PARENT DOCUMENT
    @RequestMapping(value = "/parent/{id}/document", method = RequestMethod.GET)
    public String vueParentDocument(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("parent", utilisateur);
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));
        return "parentDocuments";
    }

    // PARENT PAIEMENT
    @RequestMapping(value = "/parent/{id}/paiement", method = RequestMethod.GET)
    public String vueParentPaiement(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("parent", utilisateur);
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));
        return "parentGestionPaiement";
    }

    //PARENT DISPONIBILITE FORMATEUR
    @RequestMapping(value = "/parent/{id_parent}/liste/disponible/{id_formateur}")
    public String vueDisponibleProf(@PathVariable(name = "id_parent") Long id_parent,
            @PathVariable(name = "id_formateur") Long id_formateur, Model model) {
        Utilisateur formateur = utilisateurService.findUtilisateurByID(id_formateur);
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id_parent);
        model.addAttribute("parent", utilisateurService.findUtilisateurByID(id_parent));
        model.addAttribute("prof", formateur);
        model.addAttribute("calendrier",
                reservationService.findCalendrierReservation(formateur.getNom(), formateur.getPrenom()));
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));
        return "lire-disponible";
    }

    /*---------------ADMIN--------------*/

    // ADMIN ACCUEIL
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.GET)
    public String vueAdmin(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("admin", utilisateur);
        model.addAttribute("compteFormateur", utilisateurService.countByRole("Formateur"));
        model.addAttribute("compteMatiere", utilisateurService.CountMatiere());
        model.addAttribute("compteParent", utilisateurService.countByRole("Parent"));
        model.addAttribute("listeProf", utilisateurService.findListUtilisaterByRoleMois("Formateur"));
        model.addAttribute("listeParent", utilisateurService.findListUtilisaterByRoleMois("Parent"));
        model.addAttribute("listNotif", notificationService.rechercheNotifAdmin());
        model.addAttribute("countNotif", notificationService.countmessageAdmin());
        return "adminTabDeBord";
    }

    // ADMIN PROFIL
    @RequestMapping(value = "/admin/{id}/profil", method = RequestMethod.GET)
    public String vueAdmintProfil(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("admin", utilisateur);
        model.addAttribute("listNotif", notificationService.rechercheNotifAdmin());
        model.addAttribute("countNotif", notificationService.countmessageAdmin());
        return "adminProfil";
    }


    // ADMIN LISTE UTILISATEUR
    @RequestMapping(value = "/admin/{id}/liste", method = RequestMethod.GET)
    public String vueAdminListe(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("admin", utilisateur);
        model.addAttribute("listeProf", utilisateurService.findListeUtilisateurByRole("Formateur"));
        model.addAttribute("listeParent", utilisateurService.findListeUtilisateurByRole("Parent"));
        model.addAttribute("listNotif", notificationService.rechercheNotifAdmin());
        model.addAttribute("countNotif", notificationService.countmessageAdmin());
        return "adminListeUtilisateurs";
    }

    // ADMIN DOCUMENT
    @RequestMapping(value = "/admin/{id}/document", method = RequestMethod.GET)
    public String vueAdminDocument(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("admin", utilisateur);
        model.addAttribute("listNotif", notificationService.rechercheNotifAdmin());
        model.addAttribute("countNotif", notificationService.countmessageAdmin());
        return "AdminDocuments";
    }

    // ADMIN PAIMENT
    @RequestMapping(value = "/admin/{id}/paiement", method = RequestMethod.GET)
    public String vueAdminPaiement(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("admin", utilisateur);
        model.addAttribute("listNotif", notificationService.rechercheNotifAdmin());
        model.addAttribute("countNotif", notificationService.countmessageAdmin());
        return "adminGestionPaiement";
    }

    // ADMIN RESERVATION
    @RequestMapping(value = "/admin/{id}/reservation", method = RequestMethod.GET)
    public String vueAdminReservation(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("reservationvalidate", reservationService.listeReservations());
        model.addAttribute("admin", utilisateur);
        model.addAttribute("listNotif", notificationService.rechercheNotifAdmin());
        model.addAttribute("countNotif", notificationService.countmessageAdmin());
        return "adminReservation";
    }

    // VALIDATION RESERVATION
    @RequestMapping(value = "/admin/{admin_id}/reservation/{reservation_id}/valide", method = RequestMethod.GET)
    public String valideReservation(@PathVariable(name = "admin_id") Long admin_id,
            @PathVariable(name = "reservation_id") Long reservation_id) {

        reservationService.uptadeValidationAdmin("Validé", reservation_id);

        Reservation reservation = reservationService.findReservationById(reservation_id);
        Utilisateur formateur = utilisateurService.rechercheparentReservation(reservation.getNomprof(),
                reservation.getPrenomprof());
        Utilisateur parent = utilisateurService.rechercheparentReservation(reservation.getNomparents(),
                reservation.getPrenomparents());

        String text = "Cher abonné,\n\n" + " OiO tient à vous informer que le cours de" + reservation.getMatiere()
                + " reservé par " + parent.getNom() + " " + parent.getPrenom() + " avec l'enseignant "
                + formateur.getNom() + " " + formateur.getPrenom() + " pour la date du " + reservation.getDatecours()
                + " de " + reservation.getHeuredebut() + " à " + reservation.getHeurefin() + " a été validé. \n\n"
                + " Veuillez vérifier la validation dans votre espace personnel.\n \n" + "Nous vous remercions\n\n"
                + "L'équipe d'OIO";

                String notifReervation = "Validation du cours de " + reservation.getMatiere() + " reservé par " + parent.getNom() + " "
                + parent.getPrenom() + " avec " + formateur.getNom() + " " + formateur.getPrenom() + " Date: "
                + reservation.getDatecours() + " " + reservation.getHeuredebut() + " à " + reservation.getHeurefin();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(parent.getEmail());
        mailMessage.setSubject("Validation cours OIO");
        mailMessage.setText(text);
        mailMessage.setCc(formateur.getEmail());

        this.mailSender.send(mailMessage);

        MailNotif notifacNotif = new MailNotif();
        notifacNotif.setTexte(notifReervation);
        notifacNotif.setnotifadmin(notifReervation);
        notifacNotif.setRecu(formateur.getEmail());
        notifacNotif.setEnvoyer(parent.getEmail());

        notificationService.saveNotif(notifacNotif);

        return "redirect:/admin/" + admin_id + "/reservation";
    }

    /*---------------FORMATEUR--------------*/
    @RequestMapping(value = "/formateur/{id}", method = RequestMethod.GET)
    public String vueProfs(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur formateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("formateur", formateur);
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(formateur.getEmail(), formateur.getEmail()));
        model.addAttribute("countNotif", notificationService.countMessage(formateur.getEmail(), formateur.getEmail()));
        return "profTabDeBord";
    }

    // FORMATEUR PROFIL
    @RequestMapping(value = "/formateur/{id}/profil", method = RequestMethod.GET)
    public String vueProfil(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("formateur", utilisateur);

        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));

        return "profProfil";
    }

    // FORMATEUR RDV
    @RequestMapping(value = "/formateur/{id}/rdv", method = RequestMethod.GET)
    public String vueRDV(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur formateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("formateur", formateur);
        model.addAttribute("listreservation",
                reservationService.rechercheReservationFormateur(formateur.getNom(), formateur.getPrenom()));
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(formateur.getEmail(), formateur.getEmail()));
        model.addAttribute("countNotif", notificationService.countMessage(formateur.getEmail(), formateur.getEmail()));
        return "profGestionRDV";
    }

    // FORMATEUR RESERVATION
    @RequestMapping(value = "/formateur/{id}/reservation", method = RequestMethod.GET)
    public String vueReservation(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur formateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("formateur", formateur);
        model.addAttribute("listereservation", reservationService.reservationsStatut(formateur.getNom(),
                formateur.getPrenom(), "Disponible", "En cours"));
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(formateur.getEmail(), formateur.getEmail()));
        model.addAttribute("countNotif", notificationService.countMessage(formateur.getEmail(), formateur.getEmail()));
        return "profReservation";
    }

    // FORMATEUR PAIMENT
    @RequestMapping(value = "/formateur/{id}/gestion_paiement", method = RequestMethod.GET)
    public String vueGestP(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("formateur", utilisateur);
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));
        return "profGestionPaiement";
    }

    // FORMATEUR DOCUMENT
    @RequestMapping(value = "/formateur/{id}/document", method = RequestMethod.GET)
    public String vueDocument(@PathVariable(name = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByID(id);
        model.addAttribute("formateur", utilisateur);
        model.addAttribute("listNotif",
                notificationService.rechercheNotification(utilisateur.getEmail(), utilisateur.getEmail()));
        model.addAttribute("countNotif",
                notificationService.countMessage(utilisateur.getEmail(), utilisateur.getEmail()));
        return "profDocuments";
    }

    // MODIFIER PROFIL FORMATEUR
    @RequestMapping(value = "/ketrika/{formateur_id}", method = RequestMethod.POST)
    public String upProfil(@RequestParam("description") String description, @RequestParam("email") String email,
            @RequestParam("experience") int experience, @RequestParam("matiere") String matiere,
            @RequestParam("niveau") String niveau, @RequestParam("nom") String nom, @RequestParam("pays") String pays,
            @RequestParam("prenom") String prenom, @RequestParam("tarif") Double tarif,
            @RequestParam("telephone") String telephone, @RequestParam("ville") String ville,
            @PathVariable(name = "formateur_id") Long id) {
        utilisateurService.updateprofilprof(description, email, experience, matiere, niveau, nom, pays, prenom, tarif,
                telephone, ville, id);
        return "redirect:/formateur/" + id + "/profil";
    }

    // MODIFIER PROFIL PARENT
    @RequestMapping(value = "/tsyketrika/{parent_id}", method = RequestMethod.POST)
    public String upParent(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom,
            @RequestParam("pays") String pays, @RequestParam("ville") String ville,
            @RequestParam("telephone") String telephone, @RequestParam("email") String email,
            @RequestParam("nbrenfant") int nbrenfant, @RequestParam("description") String description,
            @PathVariable(name = "parent_id") Long id) {
        utilisateurService.updateprofilparent(nom, prenom, pays, ville, telephone, email, nbrenfant, description, id);
        return "redirect:/parent/" + id + "/profil";
    }

    /*---------------RESERVATION--------------*/
    // RESERVATION AJOUTER
    @RequestMapping(value = "/formateur/{id}/reservation/ajouter", method = RequestMethod.POST)
    public String addDispo(@PathVariable(name = "id") Long id,
            @RequestParam("datecours") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datecours,
            @RequestParam("heuredebut") @DateTimeFormat(pattern = "HH:mm") LocalTime heuredebut,
            @RequestParam("heurefin") @DateTimeFormat(pattern = "HH:mm") LocalTime heurefin) {

        Utilisateur formateur = utilisateurService.findUtilisateurByID(id);
        Reservation reservation = new Reservation();

        reservation.setDatecours(Date.valueOf(datecours));

        Date date = new Date(System.currentTimeMillis());
        reservation.setDatereservation(date);

        reservation.setHeuredebut(Time.valueOf(heuredebut));
        reservation.setHeurefin(Time.valueOf(heurefin));

        reservation.setNomprof(formateur.getNom());
        reservation.setPrenomprof(formateur.getPrenom());

        reservation.setMatiere(formateur.getMatiere());
        reservation.setNiveau(formateur.getNiveau());

        reservation.setStatutreservation("Disponible");
        reservation.setValidationadmin("En cours");

        reservation.setEstTerminer("non");

        reservationService.saveReservation(reservation);

        return "redirect:/formateur/" + formateur.getId() + "/reservation";
    }

    // RESERVATION SUPPRIMER
    @RequestMapping(value = "/formateur/{id_formateur}/reservation/{id_reservation}/Supprime", method = RequestMethod.GET)
    public String supprimerReservation(@PathVariable("id_formateur") Long id_formateur,
            @PathVariable("id_reservation") Long id_reservation) {
        reservationService.supprimerReservation(id_reservation);
        return "redirect:/formateur/" + id_formateur + "/reservation";
    }

    // Disponible --> Réservé
    @RequestMapping(value = "/parent/{parent_id}/liste/disponible/{formateur_id}/{reservation_id}", method = RequestMethod.GET)
    public String addReservationParent(@PathVariable("parent_id") Long parent_id,
            @PathVariable("formateur_id") Long formateur_id, @PathVariable("reservation_id") Long reservation_id) {

        Utilisateur parent = utilisateurService.findUtilisateurByID(parent_id);
        Utilisateur formateur = utilisateurService.findUtilisateurByID(formateur_id);
        Reservation reservation = reservationService.findReservationById(reservation_id);

        reservationService.ajouterReservationParent("Réservé", parent.getPrenom(), parent.getNom(),
                reservation.getId());

        String notifadmin = "Cours de " + reservation.getMatiere() + " reservé par " + parent.getNom() + " "
                + parent.getPrenom() + " avec " + formateur.getNom() + " " + formateur.getPrenom() + " Date: "
                + reservation.getDatecours() + " " + reservation.getHeuredebut() + " à " + reservation.getHeurefin();
                String notifFormateur = "Cours resérvé par "+ parent.getNom() + " " + parent.getPrenom()+ " Date: "
                + reservation.getDatecours() + " " + reservation.getHeuredebut() + " à " + reservation.getHeurefin();
        MailNotif notif = new MailNotif();

        notif.setnotifadmin(notifadmin);
        notif.setRecu(formateur.getEmail());
        notif.setTexte(notifFormateur);
        notificationService.saveNotif(notif);

        return "redirect:/parent/" + parent_id + "/liste/disponible/" + formateur_id;
    }

    // ###########################################################################################

    @RequestMapping(value = "/tsyannuler/{reservation_id}/{formateur_id}", method = RequestMethod.GET)
    public String annulerReservationViaProf(@PathVariable("formateur_id") Long formateur_id,
            @PathVariable("reservation_id") Long reservation_id) {
        Reservation reservation = reservationService.findReservationById(reservation_id);
        reservationService.deleteReservation(reservation.getId());
        return "redirect:/formateur/" + formateur_id + "/rdv";
    }

    // @ResponseBody
    @RequestMapping(value = "/Terminer/{reservation_id}/{formateur_id}", method = RequestMethod.GET)
    public String terminerReservationViaProf(@PathVariable("formateur_id") Long formateur_id,
            @PathVariable("reservation_id") Long reservation_id) {
        reservationService.updateTerminer("oui", reservation_id);

        Reservation reservation = reservationService.findReservationById(reservation_id);
        Utilisateur formateur = utilisateurService.rechercheparentReservation(reservation.getNomprof(),
                reservation.getPrenomprof());
        Utilisateur parent = utilisateurService.rechercheparentReservation(reservation.getNomparents(),
                reservation.getPrenomparents());

        String text = "Cher abonné,\n\n" + " OiO tient à vous informer que le cours de" + reservation.getMatiere()
                + " reservé par " + parent.getNom() + " " + parent.getPrenom() + " avec l'enseignant "
                + formateur.getNom() + " " + formateur.getPrenom() + " pour la date du " + reservation.getDatecours()
                + " de " + reservation.getHeuredebut() + " à " + reservation.getHeurefin() + " a été terminé. \n\n"
                + " Veuillez trouver dans votre espace personnel la facturation.\n \n" + "Nous vous remercions\n\n"
                + "L'équipe d'OIO";

        String notifadmin = "Terminé: Cours de " + reservation.getMatiere() + " reservé par " + parent.getNom() + " "
                + parent.getPrenom() + " avec " + formateur.getNom() + " " + formateur.getPrenom() + " Date: "
                + reservation.getDatecours() + " " + reservation.getHeuredebut() + " à " + reservation.getHeurefin();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(formateur.getEmail());
        mailMessage.setSubject("Réservation cours OIO");
        mailMessage.setText(text);
        mailMessage.setCc(parent.getEmail());

        this.mailSender.send(mailMessage);

        MailNotif notifacNotif = new MailNotif();
        notifacNotif.setTexte(notifadmin);
        notifacNotif.setRecu(formateur.getEmail());
        notifacNotif.setEnvoyer(parent.getEmail());
        notifacNotif.setnotifadmin(notifadmin);

        notificationService.saveNotif(notifacNotif);

        return "redirect:/formateur/" + formateur_id + "/rdv";
    }

    @RequestMapping(value = "/annuler/{reservation_id}/{parent_id}", method = RequestMethod.GET)
    public String annulerReservationViaParent(@PathVariable("parent_id") Long parent_id,
            @PathVariable("reservation_id") Long reservation_id) {
        Reservation reservation = reservationService.findReservationById(reservation_id);
        reservationService.updateAnnuler("Disponible", "En cours", null, null, reservation.getId());

        Utilisateur formateur = utilisateurService.rechercheparentReservation(reservation.getNomprof(),
                reservation.getPrenomprof());
        Utilisateur parent = utilisateurService.rechercheparentReservation(reservation.getNomparents(),
                reservation.getPrenomparents());

        String notifadmin = "Annulation du Cours de " + reservation.getMatiere() + " reservé par " + parent.getNom()
                + " " + parent.getPrenom() + " avec " + formateur.getNom() + " " + formateur.getPrenom() + " Date: "
                + reservation.getDatecours() + " " + reservation.getHeuredebut() + " à " + reservation.getHeurefin();

        MailNotif notif = new MailNotif();

        notif.setnotifadmin(notifadmin);
        notif.setTexte(notifadmin);
        notificationService.saveNotif(notif);

        return "redirect:/parent/" + parent_id + "/rdv";
    }

    @RequestMapping(value = "/estTerminer/{reservation_id}/{parent_id}", method = RequestMethod.GET)
    public String terminerReservationViaParent(@PathVariable("parent_id") Long parent_id,
            @PathVariable("reservation_id") Long reservation_id) {
        reservationService.updateTerminer("oui", reservation_id);

        Reservation reservation = reservationService.findReservationById(reservation_id);
        Utilisateur formateur = utilisateurService.rechercheparentReservation(reservation.getNomprof(),
                reservation.getPrenomprof());
        Utilisateur parent = utilisateurService.rechercheparentReservation(reservation.getNomparents(),
                reservation.getPrenomparents());

        String text = "Cher abonné,\n\n" + " OiO tient à vous informer que le cours de" + reservation.getMatiere()
                + " reservé par " + parent.getNom() + " " + parent.getPrenom() + " avec l'enseignant "
                + formateur.getNom() + " " + formateur.getPrenom() + " pour la date du " + reservation.getDatecours()
                + "de " + reservation.getHeuredebut() + " à " + reservation.getHeurefin() + " a été terminé. \n\n"
                + " Veuillez trouver dans votre espace personnel la facturation.\n \n" + "Nous vous remercions\n\n"
                + "L'équipe d'OIO";

        String notifadmin = "Terminé: Cours de " + reservation.getMatiere() + " reservé par " + parent.getNom() + " "
                + parent.getPrenom() + " avec " + formateur.getNom() + " " + formateur.getPrenom() + " Date: "
                + reservation.getDatecours() + " " + reservation.getHeuredebut() + " à " + reservation.getHeurefin();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(formateur.getEmail());
        mailMessage.setSubject("Réservation cours OIO");
        mailMessage.setText(text);
        mailMessage.setCc(parent.getEmail());

        this.mailSender.send(mailMessage);

        MailNotif notifacNotif = new MailNotif();
        notifacNotif.setTexte(notifadmin);
        notifacNotif.setRecu(formateur.getEmail());
        notifacNotif.setEnvoyer(parent.getEmail());
        notifacNotif.setnotifadmin(notifadmin);

        notificationService.saveNotif(notifacNotif);

        return "redirect:/parent/" + parent_id + "/rdv";
    }

    @RequestMapping(value = "/deleteparent/{parent_id}/{admin_id}", method = RequestMethod.GET)
    public String deleteParent(@PathVariable("parent_id") Long parent_id, @PathVariable("admin_id") Long admin_id) {
        utilisateurService.deleteParent(parent_id);
        return "redirect:/admin/" + admin_id + "/liste";
    }

    @RequestMapping(value = "/deleteprof/{prof_id}/{admin_id}", method = RequestMethod.GET)
    public String deleteProf(@PathVariable("prof_id") Long prof_id, @PathVariable("admin_id") Long admin_id) {
        utilisateurService.deleteProf(prof_id);
        return "redirect:/admin/" + admin_id + "/liste";
    }
}