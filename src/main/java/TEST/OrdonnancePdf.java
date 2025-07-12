package TEST;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;


public class OrdonnancePdf {

    public void generer(String nomPatient, String nomMedecin, String specialite, String traitement) {
        try {
            // Nom du fichier PDF
            String nomFichier = "ordonnance_" + nomPatient.replaceAll("\\s+", "_") + ".pdf";

            // Création du document PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            document.open();

            // Définition des polices
            Font titreFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);

            // Ajout du titre
            Paragraph titre = new Paragraph("Ordonnance Médicale", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("🩺 Médecin : " + nomMedecin + " ", normalFont));
            document.add(new Paragraph("👤 Patient : " + nomPatient, normalFont));
            document.add(new Paragraph("📅 Date : " + java.time.LocalDate.now(), normalFont));
            document.add(new Paragraph(" ")); // Espace


            Paragraph section = new Paragraph("💊 Traitement Prescrit :", titreFont);
            section.setSpacingBefore(10);
            document.add(section);

            Paragraph contenu = new Paragraph(traitement, normalFont);
            contenu.setSpacingBefore(5);
            document.add(contenu);

            document.close();
            System.out.println(" Ordonnance PDF générée : " + nomFichier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}