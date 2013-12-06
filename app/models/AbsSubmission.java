package models;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import controllers.Secured;
import play.api.mvc.Session;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * This the class model for an Abstract submission. There is a auto incrementing
 * ID, abstract details, and an user attached. \@Lob allows for large text body
 * storage. The user field is manyToOne allowing each user to have multiple
 * 
 * @author Charles Smith
 * 
 */
@Entity
public class AbsSubmission extends Model {

	/**
	 * These are the Object Fields. Please Note: Play Documentation says to make
	 * them public as the model is locked down and play automatically generates
	 * the setters and getters. (Note: I cringed at doing this)
	 */
	@Id
	public Long id;
	@Required
	public String title;
	public String cite;
	public String link;
	public String url;
	public Date publicationDate;
	@Lob
	public String abstractBody;
	@ManyToOne
	public User owner;

	public AbsSubmission(String title, String cite, String link, String url,
			Date publicationDate, String abstractBody) {
		this.title = title;
		this.cite = cite;
		this.link = link;
		this.url = url;
		this.publicationDate = publicationDate;
		this.abstractBody = abstractBody;
	}

	/**
	 * Allows outside controllers, notably Application, to call and create a new
	 * abstract. Accepts form submission and attached currently logged in user
	 * before saving
.	 * 
	 * @param abssybmission
	 *            , owner
	 */
	public static void create(AbsSubmission abssubmission, User owner) {
		abssubmission.owner = owner;
		if (owner == null) {

		}
		abssubmission.save();
	}

	/**
	 * Returns a list of all the publication by logged in user.
	 * @param email
	 * @return
	 */
	public static List<AbsSubmission> all(String email) {
		return find.where().eq("owner.email", email).findList();
		// return find.all();
	}

	/**
	 * Deletes an abstract.  Needs an Long Id passed in.
	 * @param id
	 */
	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static Finder<Long, AbsSubmission> find = new Finder<Long, AbsSubmission>(
			Long.class, AbsSubmission.class);

}