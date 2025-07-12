package org.example;

import TEST.FacturePdf;
import TEST.OrdonnancePdf;
import docteur.page.ListDocteur;

import javax.swing.*;




public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Ouvrir la fenêtre de liste des docteurs
            new ListDocteur();


            FacturePdf facture = new FacturePdf();
            facture.generer(
                    "Dr. El Amrani",
                    "Fatima Zahra",          // nom patient
                    "AB123456",              // CIN patient
                    new String[]{"Consultation", "Analyse", "Médicaments"},
                    new double[]{150.0, 80.0, 200.0},
                    "LES FARS"            // cas réduction
            );

            OrdonnancePdf pdf = new OrdonnancePdf();
            pdf.generer(
                    "Fatima Zahra",
                    "Dr. El Amrani",
                    "Cardiologue",
                    "1. Doliprane 1000mg matin et soir\n2. Repos total pendant 3 jours\n3. Revoir le médecin après 1 semaine"
            );
        });
    }
}