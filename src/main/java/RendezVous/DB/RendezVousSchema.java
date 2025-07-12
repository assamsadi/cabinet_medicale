package RendezVous.DB;

import consultation.DB.ConsultationSchema;
import org.bson.Document;
import org.bson.types.ObjectId;

public class RendezVousSchema {
    private ObjectId _id;
    private  String passion;
    private  String docteur ;
    private String diagnostic;

    public String getDiagnostique() {
        return diagnostic;
    }

    public void setDiagnostique(String diagnostique) {
        this.diagnostic = diagnostique;
    }

    public String getDocteur() {
        return docteur;
    }

    public void setDocteur(String docteur) {
        this.docteur = docteur;
    }

    public String getPassion() {
        return passion;
    }

    public void setPassion(String passion) {
        this.passion = passion;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
    public Document toDocument() {
        Document doc = new Document();
        if (this._id != null) {
            doc.append("_id", this._id);
        }
        doc.append("passion", this.passion)
                .append("docteur", this.docteur)
                .append("diagnostic", this.diagnostic);
        return doc;
    }
    public static RendezVousSchema fromDocument(Document doc) {
        RendezVousSchema consultation = new RendezVousSchema();
        consultation.set_id(doc.getObjectId("_id"));
        consultation.setPassion(doc.getString("passion"));
        consultation.setDocteur(doc.getString("docteur"));
        consultation.setDiagnostique(doc.getString("diagnostic"));
        return consultation;
    }
}
