package traits
import play.api.mvc._
import controllers.routes
import controllers.UserController
import models.User

trait Secured {
	def username(request: RequestHeader) = request.session.get(Security.username)
	
	def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login)

	def withAuth(f: => String => Request[AnyContent] => Result) = {
		Security.Authenticated(username, onUnauthorized) { user =>
			Action(request => f(user)(request))
		}
	}

	def withUser(f: User => Request[AnyContent] => Result) = withAuth { email => implicit request =>
		UserController.getUserByEmail(email).map { user =>
			f(user)(request)
		}.getOrElse(onUnauthorized(request))
	}
}