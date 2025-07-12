package Ordonnance.DB;

import com.mongodb.client.MongoClient;
import consultation.DB.ConsultationSchema;
import org.bson.Document;
import org.example.Service;

public class OrdonnanceService extends Service<OrdonnanceSchema> {
    public OrdonnanceService(MongoClient mongoClient, String nameData, String nameColection) {
        super(mongoClient, nameData, nameColection);
    }

    @Override
    public Document toDocument(OrdonnanceSchema entity) {
        if (entity == null) {
            return null;
        }
        return entity.toDocument();
    }



    @Override
    public OrdonnanceSchema fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        return OrdonnanceSchema.fromDocument(doc);
    }
}
