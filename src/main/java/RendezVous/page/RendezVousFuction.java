package RendezVous.page;

import RendezVous.DB.RendezVousSchema;
import RendezVous.DB.RendezVousService;
import TEST.OrdonnancePdf;
import com.mongodb.client.MongoClients;
import consultation.DB.ConsultationSchema;
import consultation.DB.ConsultationService;

import javax.swing.*;
import java.util.List;

public class RendezVousFuction {
    boolean  dtoDiagno(JTextField name , JLabel errorName ){
        if (name.getText().isEmpty()){
            errorName.setText("Diagnostic is required");
            return true;
        }
        else {
            errorName.setText("");
            return false;


        }
    }
    void  submit(String passion , String docteur , String diagnostic){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            System.out.println("dsssssssssssssss");
            RendezVousService s = new RendezVousService(mongoClient, "ASASA" , "diagnostic");
            RendezVousSchema u = new RendezVousSchema();
            u.setPassion(passion);
            u.setDocteur(docteur);
            u.setDiagnostique(diagnostic);
            s.create(u);


        }

    }
    List<RendezVousSchema> paginate(String nameD){
        List<RendezVousSchema>  P;

        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            RendezVousService s = new RendezVousService(mongoClient, "ASASA" , "diagnostic");
            if(nameD != "")
                P= s.paginate(0,34 , nameD );
            else
            P= s.paginate(0,34  );



        }
        return P;

    }

    public RendezVousSchema find(String id){
        RendezVousSchema u = null;
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            RendezVousService s = new RendezVousService(mongoClient, "ASASA" , "diagnostic");
            u= s.findById(id);
        }
        return  u;
    }
    public void  delete(String id ){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            RendezVousService s = new RendezVousService(mongoClient, "ASASA" , "diagnostic");
            s.delete(id);
        }
    }
    void  edite(String id ,String passion , String docteur , String diagnostic){

        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            System.out.println("dsssssssssssssss");
            RendezVousService s = new RendezVousService(mongoClient, "ASASA" , "diagnostic");
            RendezVousSchema u = new RendezVousSchema();
            u.setPassion(passion);
            u.setDocteur(docteur);
            u.setDiagnostique(diagnostic);
            s.update(id,u);

        }

    }
}
