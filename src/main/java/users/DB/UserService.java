package users.DB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.Service;

import java.util.ArrayList;
import java.util.List;

public class UserService extends Service<UserSchema> {

    public UserService(MongoClient mongoClient, String databaseName, String collectionName) {
        super(mongoClient, databaseName, collectionName);
    }

    @Override
    public Document toDocument(UserSchema user) {
        if (user == null) {
            return null;
        }
        return user.toDocument(); // Delegate to UserSchema's method
    }

    @Override
    public UserSchema fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        return UserSchema.fromDocument(doc); // Delegate to UserSchema's static method
    }


}