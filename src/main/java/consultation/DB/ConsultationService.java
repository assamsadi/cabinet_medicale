package consultation.DB;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.example.Service;
import users.DB.UserSchema;

import java.security.Provider;

public class ConsultationService  extends Service<ConsultationSchema> {
    public ConsultationService(MongoClient mongoClient, String databaseName, String collectionName) {
        super(mongoClient, databaseName, collectionName);
    }

    @Override
    public Document toDocument(ConsultationSchema consultation) {
        if (consultation == null) {
            return null;
        }
        return consultation.toDocument();
    }

    @Override
    public ConsultationSchema fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        return ConsultationSchema.fromDocument(doc);
    }

}
