package docteur.DB;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.example.Service;
import users.DB.UserSchema;

public class DocteurService  extends Service<DocteurSchema> {
    public DocteurService(MongoClient mongoClient, String nameData, String nameColection) {
        super(mongoClient, nameData, nameColection);
    }

    @Override
    public Document toDocument(DocteurSchema docteur) {
        if (docteur == null) return null;
        return docteur.toDocument();
    }


    @Override
    public DocteurSchema fromDocument(Document doc) {
        return DocteurSchema.fromDocument(doc);
    }


}
