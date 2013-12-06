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

@Entity
public class AbsSubmission extends Model {

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
	
	
	   public AbsSubmission(String title, String cite, String link, String url, Date publicationDate, String abstractBody) {
	        this.title = title;
	        this.cite = cite;
	        this.link = link;
	        this.url = url;
	        this.publicationDate = publicationDate;
	        this.abstractBody = abstractBody;
	    }
	   
	   public static void create(AbsSubmission abssubmission, User owner) {
		   abssubmission.owner = owner;
		   if(owner == null){
			  
		   }
		   abssubmission.save();
		 }

	public static List<AbsSubmission> all(String email) {
		if(email != null){
		return find.where().eq("owner.email", email).findList();
		}
		return find.all();
		}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static Finder<Long, AbsSubmission> find = new Finder<Long, AbsSubmission>(Long.class,
			AbsSubmission.class);

}