package controllers;

import static play.data.Form.form;
import models.AbsSubmission;
import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import views.html.helper.form;

/**
 * Controller for entire Application
 * 
 * @author Charles Smith
 * 
 */
public class Application extends Controller {

	/**
	 * This is the main page when you first start the application. Forwards to
	 * login page
	 * 
	 * @return
	 */
	public static Result index() {
		return redirect(routes.Application.login());
	}

	/**
	 * This is the view of submitted abstracts. Is protected from being accessed
	 * without being logged in.
	 * 
	 * @return
	 */
	@Security.Authenticated(Secured.class)
	public static Result viewsubmitted() {
		return ok(views.html.submitted.render(
				AbsSubmission.all(request().username()),
				User.find.byId(request().username())));
	}

	/**
	 * This is the main page of the application containing the form for abstract
	 * submission. Is protected from being accessed without being logged in.
	 * 
	 * @return
	 */
	@Security.Authenticated(Secured.class)
	public static Result absSubmission() {
		return ok(views.html.index.render(
				AbsSubmission.all(request().username()), absSubmissionForm,
				User.find.byId(request().username())));
	}

	/**
	 * This the POST when a Abstract is submitted. If something is wrong,
	 * redirects user back to form. Otherwise redirects them to Abstract views.
	 * Is protected from being accessed without being logged in.
	 * 
	 * @return
	 */
	@Security.Authenticated(Secured.class)
	public static Result newabsSubmission() {
		Form<AbsSubmission> filledForm = absSubmissionForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render(
					AbsSubmission.all(request().username()), filledForm,
					User.find.byId(request().username())));
		} else {
			AbsSubmission.create(filledForm.get(),
					User.find.byId(request().username()));
			return redirect(routes.Application.viewsubmitted());
		}
	}

	/**
	 * This is the GET request for a Abstract deletion. Is protected from being
	 * accessed without being logged in.
	 * 
	 * @param id
	 * @return
	 */
	@Security.Authenticated(Secured.class)
	public static Result deleteabsSubmission(Long id) {
		AbsSubmission.delete(id);
		return redirect(routes.Application.absSubmission());
	}

	/**
	 * Static Form Object
	 */
	static Form<AbsSubmission> absSubmissionForm = Form
			.form(AbsSubmission.class);

	/**
	 * This is the GET request when the application initial loads.
	 * 
	 * @return
	 */
	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	/**
	 * This is the POST request when login credentials are entered. If correct,
	 * forwards them onward, otherwise redirects back to login page.
	 * 
	 * @return
	 */
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session("email", loginForm.get().email);
			return redirect(routes.Application.absSubmission());
		}
	}

	/**
	 * Clears session and directs back to the login
	 * 
	 * @return
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Application.login());
	}

	/**
	 * Class representing the login form. Method is used to check if someone
	 * entered the correct credentials.
	 * 
	 * @author Charles Smith
	 * 
	 */
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

	/**
	 * This is the GET for the signup page.
	 * 
	 * @return
	 */
	public static Result signup() {
		return ok(views.html.signup.render(signUpForm));
	}

	/**
	 * This is the POST for the signup page. If something is wrong with the
	 * form, return them to there, otherwise forwards the user to the login
	 * page.
	 * 
	 * @return
	 */
	public static Result newUser() {
		Form<User> filledForm = signUpForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.signup.render(filledForm));
		} else {
			User.create(filledForm.get());
			return redirect(routes.Application.login());
		}
	}

}