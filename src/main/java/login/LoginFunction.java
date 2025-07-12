package login;

import com.mongodb.client.MongoClients;
import docteur.DB.DocteurSchema;
import docteur.DB.DocteurService;
import docteur.page.DocteurFunction;

import java.util.Objects;

public class LoginFunction {

    public String login (String name , String password){
        DocteurSchema d ;
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            DocteurService s = new DocteurService(mongoClient, "ASASA", "Docteurs");
            if(Objects.equals(name, "admin") && Objects.equals(password, "admin")){
                System.out.println("admin");

                return "admin";
            }
            else {

            d = s.findByName(name);
            if (Objects.equals(password, d.getPassword())){
                System.out.println("doooooooooooooon");
                return d.getName();
            }
            }

        }
                return "";

    }
}
