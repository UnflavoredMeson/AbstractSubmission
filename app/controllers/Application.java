package controllers;

import static play.data.Form.form;
import models.AbsSubmission;
import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import views.html.helper.form;

public class Application extends Controller {
	
    public static Result index() {
		return redirect(routes.Application.login());
    }

    @Security.Authenticated(Secured.class)
	public static Result absSubmission() {
		return ok(views.html.index.render(AbsSubmission.all(request().username()), absSubmissionForm, User.find.byId(request().username())));
	}

    @Security.Authenticated(Secured.class)
	public static Result newabsSubmission() {
		  Form<AbsSubmission> filledForm = absSubmissionForm.bindFromRequest();
		  if(filledForm.hasErrors()) {
		    return badRequest(
		      views.html.index.render(AbsSubmission.all(request().username()), filledForm, User.find.byId(request().username())));
		  } else {
			  AbsSubmission.create(filledForm.get(), User.find.byId(request().username()));
		    return redirect(routes.Application.absSubmission());  
		  }
	}

    @Security.Authenticated(Secured.class)
	public static Result deleteabsSubmission(Long id) {
		AbsSubmission.delete(id);
		  return redirect(routes.Application.absSubmission());
	}

	static Form<AbsSubmission> absSubmissionForm = Form.form(AbsSubmission.class);
	
	public static Result login() {
        return ok(
            login.render(form(Login.class))
        );
    }
    
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                routes.Application.absSubmission()
            );
        }
    }
    
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.login()
        );
    }
    
    public static class Login {

        public String email;
        public String password;
        
        public String validate() {
            if (User.authenticate(email, password) == null) {
              return "Invalid user or password";
            }
            return null;
        }
    }

	static Form<User> signUpForm = Form.form(User.class);
	
	public static Result signup() {
        return ok(views.html.signup.render(signUpForm));
    }
	
	public static Result newUser() {
		  Form<User> filledForm = signUpForm.bindFromRequest();
		  if(filledForm.hasErrors()) {
		    return badRequest(
		    		views.html.signup.render(filledForm));
		  } else {
			  User.create(filledForm.get());
		    return redirect(routes.Application.login());  
		  }
	}

}