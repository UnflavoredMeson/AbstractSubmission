package models;
 
import java.util.*;

import javax.persistence.*;

import play.db.ebean.Model;
 
@Entity
public class User extends Model {
 
	@Id
    public String email;
    public String password;
    public String fullname;
    
    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }
    
	   public static void create(User user) {
		   user.save();
		 }
	   
	    public static User findByEmail(String email) {
	        return find.where().eq("email", email).findUnique();
	    }

    public static Finder<String,User> find = new Finder<String,User>(
        String.class, User.class
    ); 
    
    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
    
}