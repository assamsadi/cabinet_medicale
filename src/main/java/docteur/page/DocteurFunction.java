package docteur.page;

import com.mongodb.client.MongoClients;
import docteur.DB.DocteurSchema;
import docteur.DB.DocteurService;
import docteur.DB.Horaire;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DocteurFunction {

    public boolean dtoName(JTextField input, JLabel errorLabel) {
        if (input.getText().isEmpty()) {
            errorLabel.setText("Name is required");
            return true;
        } else {
            try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
                DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
                DocteurSchema d = s.findByName(input.getText());
                if( d == null){
                    return false;
                }

            }
            errorLabel.setText("");
            return false;
        }
    }

    public boolean dtoPhone(JTextField input, JLabel errorLabel) {
        if (input.getText().isEmpty()) {
            errorLabel.setText("Phone is required");
            return true;
        } else {
            errorLabel.setText("");
            return false;
        }
    }public boolean dtoPassword(JTextField input, JLabel errorLabel) {
        if (input.getText().isEmpty()) {
            errorLabel.setText("Phone is required");
            return true;
        } else {
            errorLabel.setText("");
            return false;
        }
    }
    public String[] allDocteur(){
        List<String> doc = new ArrayList<>();

        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            List<DocteurSchema> us = s.paginate(0, 200); // fixed parenthesis

            for (DocteurSchema a : us) {
                doc.add(a.getName());
            }

        }

        return doc.toArray(new String[0]);

    }
    public String[] allDocteur(String spi){
        List<String> doc = new ArrayList<>();

        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            List<DocteurSchema> us = s.paginate(0, 200); // récupère les docteurs (à adapter si besoin)

            for (DocteurSchema a : us) {
                if (a.getName() != null && a.getName().contains(spi)) {
                    doc.add(a.getName());
                }
            }

        }

        return doc.toArray(new String[0]);

    }

    public boolean dtoSpecialty(JTextField input, JLabel errorLabel) {
        if (input.getText().isEmpty()) {
            errorLabel.setText("Specialty is required");
            return true;
        } else {
            errorLabel.setText("");
            return false;
        }
    }

    public void submitDocteur(String name, String phone, String specialty,String password) {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            DocteurSchema d = new DocteurSchema();
            d.setName(name);
            d.setPhone(phone);
            d.setSpecialty(specialty);
            d.setPassword(password);

            s.create(d);
        }
    }

    // 🔁 Corrigée : cette méthode met aussi à jour jourTravail et horaires
    public void edit(String id, String name, String specialty, String phone,String password ) {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            DocteurSchema d = new DocteurSchema();
            d.setName(name);
            d.setSpecialty(specialty);
            d.setPhone(phone);
            d.setPassword(password);
            s.update(id, d);
        }
    }

    public List<DocteurSchema> paginate() {
        List<DocteurSchema> P;
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            P = s.paginate(0, 34);
        }
        return P;
    }
    public List<DocteurSchema> paginate(String f) {
        List<DocteurSchema> P;
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            P = s.paginate(0, 34 ,f);
        }
        return P;
    }

    public void delete(String id) {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            s.delete(id);
        }
    }

    public DocteurSchema find(String id) {
        DocteurSchema d = null;
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            d = s.findById(id);
        }
        return d;
    }
}