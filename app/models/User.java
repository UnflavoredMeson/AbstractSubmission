package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.Model;

/**
 * This the class model for a single user. Each user has a email, password, and
 * name. No Getters and Setters are needed because Play does that automatically.
 * 
 * @author Charles Smith
 * 
 */
@Entity
public class User extends Model {

	/**
	 * These are the Object Fields. Please Note: Play Documentation says to make
	 * them public as the model is locked down and play automatically generates
	 * the setters and getters. (Note: I cringed at doing this)
	 */
	@Id
	public String email;
	public String password;
	public String fullname;

	public User(String email, String password, String fullname) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
	}

	/**
	 * Allows outside controllers, notably Application, to call and create a new
	 * user
	 * 
	 * @param user
	 */
	public static void create(User user) {
		user.save();
	}

	public static User findByEmail(String email) {
		return find.where().eq("email", email).findUnique();
	}

	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);

	public static User authenticate(String email, String password) {
		return find.where().eq("email", email).eq("password", password)
				.findUnique();
	}

}