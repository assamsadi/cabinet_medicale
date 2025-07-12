package Ordonnance.page;

import Ordonnance.DB.OrdonnanceSchema;
import Ordonnance.DB.OrdonnanceService;
import com.mongodb.client.MongoClients;
import users.DB.UserSchema;
import users.DB.UserService;

import java.util.List;

public class OrdonnanceFuction {
    void  submit(String   docteur, String passion , String medicaments ){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            System.out.println("dsssssssssssssss");
            OrdonnanceService s = new OrdonnanceService(mongoClient, "ASASA" , "Ordonnance");
            OrdonnanceSchema u = new OrdonnanceSchema();
            u.setPation(passion);
            u.setDocteur(docteur);
            u.setMedicament(medicaments);

            s.create(u);

        }


    }
    List<OrdonnanceSchema> paginate(){
        List<OrdonnanceSchema> P;

        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            OrdonnanceService s = new OrdonnanceService(mongoClient, "ASASA" , "Ordonnance");
            OrdonnanceSchema u = new OrdonnanceSchema();
            P= s.paginate(0,19);


        }
        return P;

    }
    List<OrdonnanceSchema> paginateByDoc(String name){
        List<OrdonnanceSchema> P;

        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            OrdonnanceService s = new OrdonnanceService(mongoClient, "ASASA" , "Ordonnance");
            OrdonnanceSchema u = new OrdonnanceSchema();
            P= s.paginate(0,19 , name);


        }
        return P;

    }
    public void  delete(String id ){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            OrdonnanceService s = new OrdonnanceService(mongoClient, "ASASA" , "Ordonnance");
            s.delete(id);
        }
    }
}
