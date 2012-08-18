package traits
import play.api.mvc._
import controllers.routes
import controllers.UserController
import models.User

trait Secured {
	def userId(request: RequestHeader) = request.session.get("userId")
	
	def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login)

	def withAuth(f: => String => Request[AnyContent] => Result) = {
		Security.Authenticated(userId, onUnauthorized) { user =>
			Action(request => f(user)(request))
		}
	}

	def withUser(f: User => Request[AnyContent] => Result) = withAuth { userId => implicit request =>
		UserController.getUserById(userId.toInt).map { user =>
			f(user)(request)
		}.getOrElse(onUnauthorized(request))
	}
}