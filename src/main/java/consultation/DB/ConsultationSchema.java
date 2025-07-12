package consultation.DB;

import org.bson.Document;
import org.bson.types.ObjectId;
import users.DB.UserSchema;

import java.util.Date;

public class ConsultationSchema {
    private ObjectId _id;
    private  String passion;
    private  String docteur ;
    private String time;
    private String problem;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Document toDocument() {
        Document doc = new Document();
        if (this._id != null) {
            doc.append("_id", this._id);
        }
        doc.append("passion", this.passion)
                .append("docteur", this.docteur)
                .append("problem", this.problem)
                .append("time", this.time);
        return doc;
    }

    // Create from Document
    public static ConsultationSchema fromDocument(Document doc) {
        ConsultationSchema consultation = new ConsultationSchema();
        consultation.set_id(doc.getObjectId("_id"));
        consultation.setPassion(doc.getString("passion"));
        consultation.setDocteur(doc.getString("docteur"));
        consultation.setTime(doc.getString("time"));
        consultation.setProbleme(doc.getString("problem"));
        return consultation;
    }

    public String getProbleme() {
        return problem;
    }

    public void setProbleme(String probleme) {
        this.problem= probleme;
    }
}
