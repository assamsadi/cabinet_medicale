package users.page;

import com.mongodb.client.MongoClients;
import users.DB.UserSchema;
import users.DB.UserService;

import javax.swing.*;
import java.util.List;

public class UserFunctions {
    boolean  dtoName(JTextField name , JLabel errorName ){
        if (name.getText().isEmpty()){
            errorName.setText("mane is required");
            return true;
        }
        else {
            errorName.setText("");
            return false;


        }
    }
    boolean  dtoPhone(JTextField name , JLabel errorName ){
        if (name.getText().isEmpty()){
            errorName.setText("phone is required");
            return true;

        }
        else {
            errorName.setText("");
            return false;

        }
    }
    boolean  dtoRole(JTextField name , JLabel errorName ){
        if (name.getText().isEmpty()){
            errorName.setText("Role is required");
            return true;

        }
        else {
            errorName.setText("");
            return false;

        }
    }
    boolean  dtoCin(JTextField name , JLabel errorName ){
        if (name.getText().isEmpty()){
            errorName.setText("CIN is required");
            return true;

        }
        else {
            errorName.setText("");
            return false;

        }
    }
    void  submit(String name , String phone , String cin , String role){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            UserService s = new UserService(mongoClient, "ASASA" , "Users");
            UserSchema u = new UserSchema();
            u.setRole(role);
            u.setName(name);
            u.setCIN(cin);
            u.setPhone(phone);
            s.create(u);

        }

    }
    void  edit(String id, String name , String phone , String cin , String role){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            UserService s = new UserService(mongoClient, "ASASA" , "Users");
            UserSchema u = new UserSchema();
            u.setRole(role);
            u.setName(name);
            u.setCIN(cin);
            u.setPhone(phone);
            s.update(id , u);

        }
    }
    List<UserSchema>  paginate(){
        List<UserSchema>  P;

        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            UserService s = new UserService(mongoClient, "ASASA" , "Users");
            UserSchema u = new UserSchema();
            P= s.paginate(0,34);


        }
        return P;

    }
    List<UserSchema>  paginatef(String f){
        List<UserSchema>  P;

        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            UserService s = new UserService(mongoClient, "ASASA" , "Users");
            UserSchema u = new UserSchema();
            P= s.paginate(0,34 , f);


        }
        return P;

    }
    public void  delete(String id ){
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            UserService s = new UserService(mongoClient, "ASASA" , "Users");
            s.delete(id);
        }
    }
    public UserSchema find(String id){
        UserSchema u = null;
        try(var mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            UserService s = new UserService(mongoClient, "ASASA" , "Users");
            u= s.findById(id);
        }
        return  u;
    }

}

