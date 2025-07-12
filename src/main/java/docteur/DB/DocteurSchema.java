package docteur.DB;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class DocteurSchema {
    private ObjectId _id;
    private String name;
    private String specialty;
    private String phone;
    private String password;





    public Document toDocument() {
        Document doc = new Document();
        if (_id != null) {
            doc.append("_id", _id);
        }
        doc.append("name", name)
                .append("specialty", specialty)
                .append("phone", phone)
                .append("password", password);
        List<Document> horairesDoc = new ArrayList<>();

        doc.append("horaires", horairesDoc);

        // 🔍 Ajout du log ici
        System.out.println("📦 Document final envoyé à MongoDB : " + doc.toJson());

        return doc;
    }

    public static DocteurSchema fromDocument(Document doc) {
        DocteurSchema docteur = new DocteurSchema();
        docteur.set_id(doc.getObjectId("_id"));
        docteur.setName(doc.getString("name"));
        docteur.setSpecialty(doc.getString("specialty"));
        docteur.setPhone(doc.getString("phone"));
        docteur.setPassword(doc.getString("password"));



        return docteur;
    }

    // Getters et setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


}