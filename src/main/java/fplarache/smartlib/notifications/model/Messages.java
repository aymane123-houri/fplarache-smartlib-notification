package fplarache.smartlib.notifications.model;

public class Messages {
    public String getConfirmationMessage(Emprunt emprunt){
        return "Salut " + emprunt.getUser().getNom() + ",\n"
                + "Nous avons le plaisir de vous confirmer que vous avez emprunté le livre " + emprunt.getLivre().getTitre() + ".\n"
                + "Nous vous rappelons que la date de retour de ce livre est fixée au " + emprunt.getDateEmprunt() + ". "
                + "Merci de bien vouloir restituer le livre avant cette date afin de permettre à d’autres utilisateurs d’en profiter.\n"
                + "En cas de retard, des frais supplémentaires peuvent être appliqués conformément à notre politique de prêt.\n"
                + "Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter à libsmart66@gmail.com.\n"
                + "Merci pour votre coopération et bonne lecture !\n"
                + "Cordialement,\n"
                + "L'équipe de la SmartLib.";
    }

    public String getAvailabilityMessage(User user, Livre livre){
        return "Salut " + user.getNom() + ",\n"
                + "Nous avons le plaisir de vous informer qu'un nouveau livre correspondant à vos intérêts est maintenant disponible dans notre bibliothèque. \n"
                + "Titre du livre: " + livre.getTitre() + "\n"
                + "Auteur: " + livre.getAuteur() + "\n"
                + "Catégorie: " + livre.getGenre() + "\n"
                + "N’hésitez pas à consulter notre bibliothèque et à emprunter ce livre avant qu’il ne soit pris par un autre utilisateur ! \n"
                + "Pour plus d’informations ou pour réserver ce livre, veuillez vous connecter à votre compte sur notre site. \n"
                + "Cordialement,\n"
                + "L'équipe de la SmartLib.";

    }

    public String getRappelMessage(OverdueUser overdueUser){

        StringBuilder message = new StringBuilder();

        message.append("Salut ").append(overdueUser.getUser().getNom()).append(",\n")
                .append("Nous espérons que vous avez apprécié les livres que vous avez empruntés à notre bibliothèque. Nous souhaitons vous rappeler que la date limite de retour de vos emprunts a été dépassée. Afin d'éviter des pénalités, merci de bien vouloir retourner les livres dès que possible. \n\n")
                .append("Détails des emprunts en retard: \n");

        // Iterate through the list of Emprunt objects
        for (Emprunt emprunt : overdueUser.getEmprunts()) {
            message.append("Titre du livre: ").append(emprunt.getLivre().getTitre()).append("\n")
                    .append("Date d'échéance: ").append(emprunt.getDateRetour()).append("\n")
                    .append("Date de l'emprunt: ").append(emprunt.getDateEmprunt()).append("\n\n");
        }

        message.append("Si vous avez déjà rendu ces livres ou si vous avez des questions, n'hésitez pas à nous contacter à libsmart66@gmail.com. \n")
                .append("Merci pour votre coopération et au plaisir de vous accueillir prochainement dans notre bibliothèque. \n")
                .append("Cordialement,\n")
                .append("L'équipe de la SmartLib.");

        return message.toString();

    }

}
