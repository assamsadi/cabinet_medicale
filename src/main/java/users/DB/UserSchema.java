package users.DB;

import org.bson.Document;
import org.bson.types.ObjectId;

public class UserSchema {
    private ObjectId _id;
    private String name;
    private String phone;
    private String CIN;
    private String role;

    // Convert to Document

    public Document toDocument() {
        Document doc = new Document();
        if (this._id != null) {
            doc.append("_id", this._id);
        }
        doc.append("name", this.name)
                .append("phone", this.phone)
                .append("CIN", this.CIN)
                .append("role", this.role);
        return doc;
    }

    // Create from Document
    public static UserSchema fromDocument(Document doc) {
        UserSchema user = new UserSchema();
        user.set_id(doc.getObjectId("_id"));
        user.setName(doc.getString("name"));
        user.setPhone(doc.getString("phone"));
        user.setCIN(doc.getString("CIN"));
        user.setRole(doc.getString("role"));
        return user;
    }

    // Getters and Setters
    public ObjectId get_id() {
        return this._id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}