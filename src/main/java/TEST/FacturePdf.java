package TEST;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class FacturePdf {


    public void generer(String medecin, String patientNom, String patientCIN, String[] services, double[] prix, String casReduction){
        try {
            Document document = new Document();
            String fileName = "facture_" + medecin.replace(" ", "_") + ".pdf";
            PdfWriter.getInstance(document, new java.io.FileOutputStream(fileName));
            document.open();

            // Titre
            document.add(new Paragraph("Facture médicale"));
            document.add(new Paragraph("Médecin : " + medecin));

            document.add(new Paragraph("Patient : " + patientNom));
            document.add(new Paragraph("CIN patient : " + patientCIN));
            document.add(new Paragraph("Convention : " + casReduction));

            document.add(new Paragraph(" "));

            // Tableau des services
            PdfPTable table = new PdfPTable(2);
            table.addCell("Service");
            table.addCell("Prix (DHS)");

            double total = 0;
            for (int i = 0; i < services.length; i++) {
                table.addCell(services[i]);
                table.addCell(String.format("%.2f", prix[i]));
                total += prix[i];
            }
            document.add(table);

            // Appliquer réduction selon convention
            double reduction = 0;
            if (casReduction.equalsIgnoreCase("LES FARS")) {
                reduction = 0.20; // 20% de réduction
            } else if (casReduction.equalsIgnoreCase("LIMPF")) {
                reduction = 0.15; // 15% de réduction
            }
            double montantReduction = total * reduction;
            double totalApresReduction = total - montantReduction;

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total avant réduction : " + String.format("%.2f", total) + " DHS"));
            document.add(new Paragraph("Réduction (" + (int)(reduction * 100) + "%) : -" + String.format("%.2f", montantReduction) + " DHS"));
            document.add(new Paragraph("Total à payer : " + String.format("%.2f", totalApresReduction) + " DHS"));

            document.close();

            System.out.println(" Facture PDF générée : " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}