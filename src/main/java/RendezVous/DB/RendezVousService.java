package RendezVous.DB;

import com.mongodb.client.MongoClient;
import consultation.DB.ConsultationSchema;
import org.bson.Document;
import org.example.Service;

public class RendezVousService extends Service<RendezVousSchema> {
    public RendezVousService(MongoClient mongoClient, String nameData, String nameColection) {
        super(mongoClient, nameData, nameColection);
    }

    @Override
    public Document toDocument(RendezVousSchema entity) {
        if (entity == null) {
            return null;
        }
        return entity.toDocument();
    }

    @Override
    public RendezVousSchema fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        return RendezVousSchema.fromDocument(doc);
    }
}
