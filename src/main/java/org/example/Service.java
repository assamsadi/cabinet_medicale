package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import users.DB.UserSchema;

import java.util.ArrayList;
import java.util.List;


public abstract class Service <T>{
    protected final MongoCollection<Document> collection;
    public abstract Document toDocument(T entity);
    public abstract T fromDocument(Document doc);

    public  Service( MongoClient mongoClient  , String nameData  , String nameColection){
    MongoDatabase db =mongoClient.getDatabase(nameData);
    this.collection = db.getCollection(nameColection);
}

public void create(T object){
        Document doc = toDocument(object);
        collection.insertOne(doc);
}

public  T findById(String id ){
    Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
    if (doc!= null )
        return fromDocument(doc);
    else
        return  null;
    }
    public  T findByName(String name ){
        Document doc = collection.find(Filters.eq("name", name)).first();
        if (doc!= null )
            return fromDocument(doc);
        else
            return  null;
    }


    public void update(String id, T newObject) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        Document updateDoc = toDocument(newObject);
        Bson update = new Document("$set", updateDoc);
        collection.findOneAndUpdate(filter, update);
    }



    public void delete(String id ){
         collection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));
    }

    public List<T> paginate(int page, int pageSize) {
        List<T> results = new ArrayList<>();
        collection.find()
                .skip(page * pageSize)
                .limit(pageSize)
                .forEach(doc -> results.add(fromDocument(doc)));
        return results;
    }
    public List<T> paginate(int page, int pageSize, String name) {
        List<T> results = new ArrayList<>();

        Bson filter = Filters.eq("docteur", name); // Filter by name

        collection.find(filter)
                .skip(page * pageSize)
                .limit(pageSize)
                .forEach(doc -> results.add(fromDocument(doc)));

        return results;
    }


}