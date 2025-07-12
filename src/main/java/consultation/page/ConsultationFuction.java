package consultation.page;

import com.mongodb.client.MongoClients;
import consultation.DB.ConsultationSchema;
import consultation.DB.ConsultationService;
import users.DB.UserSchema;
import users.DB.UserService;

import javax.swing.*;
import java.util.List;

public class ConsultationFuction {
    boolean  dtoProblem(JTextField name , JLabel errorName ){
        if (name.getText().isEmpty()){
            errorName.setText("problem is required");
            return true;
        }
        else {
            errorName.setText("");
            return false;


        }
    }
    boolean  dtoDate(JTextField name , JLabel errorName ){
        if (name.getText().isEmpty()){
            errorName.setText("date is required");
            return true;
        }
        else {
            errorName.setText("");
            return false;


        }
    }
    boolean  dtoDoctur(JTextField name , JLabel errorName ){
        if (name.getText().isEmpty()){
            errorName.setText("mane is required");
            return true;
        }
        else {
            errorName.setText("");
            return false;


        }
    }
    void  submit(String passion , String docteur , String time , String problem){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            System.out.println("dsssssssssssssss");
            ConsultationService s = new ConsultationService(mongoClient, "ASASA" , "consultation");
            ConsultationSchema u = new ConsultationSchema();
            u.setPassion(passion);
            u.setDocteur(docteur);
            u.setTime(time);
            u.setProbleme(problem);
            s.create(u);

        }

    }
    void  edite(String id , String passion , String docteur , String time , String problem){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            System.out.println("dsssssssssssssss");
            ConsultationService s = new ConsultationService(mongoClient, "ASASA" , "consultation");
            ConsultationSchema u = new ConsultationSchema();
            u.setProbleme(passion);
            u.setDocteur(docteur);
            u.setTime(time);
            u.setProbleme(problem);
            s.update(id,u);

        }

    }
        List<ConsultationSchema> paginate(){
            List<ConsultationSchema>  P;

            try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
                ConsultationService s = new ConsultationService(mongoClient, "ASASA" , "consultation");
                P= s.paginate(0,34 );


            }
            return P;

        }
    List<ConsultationSchema> paginateByDoc(String name){
        List<ConsultationSchema>  P;

        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            ConsultationService s = new ConsultationService(mongoClient, "ASASA" , "consultation");
            P= s.paginate(0,34  , name);


        }
        return P;

    }
    public void  delete(String id ){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            ConsultationService s = new ConsultationService(mongoClient, "ASASA" , "consultation");
            s.delete(id);
        }
    }
    public ConsultationSchema find(String id){
        ConsultationSchema u = null;
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            ConsultationService s = new ConsultationService(mongoClient, "ASASA" , "consultation");
            u= s.findById(id);
        }
        return  u;
    }
}
