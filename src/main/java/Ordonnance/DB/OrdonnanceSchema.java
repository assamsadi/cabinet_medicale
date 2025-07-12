package Ordonnance.DB;

import consultation.DB.ConsultationSchema;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class OrdonnanceSchema {
    private ObjectId _id;
    private String docteur ;
    private String passion ;
    private String medicament;

    public String getDocteur() {
        return docteur;
    }

    public void setDocteur(String docteur) {
        this.docteur = docteur;
    }

    public String getPation() {
        return passion;
    }
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
    public void setPation(String pation) {
        this.passion = pation;
    }

    public String getMedicament() {

        return medicament;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }
    public Document toDocument() {
        Document doc = new Document();
        if (this._id != null) {
            doc.append("_id", this._id);
        }
        doc.append("passion", this.passion)
                .append("docteur", this.docteur)
                .append("medicament", this.medicament);
        return doc;
    }
    public static OrdonnanceSchema fromDocument(Document doc) {
        OrdonnanceSchema consultation = new OrdonnanceSchema();
        consultation.set_id(doc.getObjectId("_id"));
        consultation.setPation(doc.getString("passion"));
        consultation.setMedicament(doc.getString("medicament"));
        return consultation;
    }
}
