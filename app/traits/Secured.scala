package traits
import play.api.mvc._
import controllers.routes

trait Secured {
	def username(request: RequestHeader) = request.session.get(Security.username)
	
	def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login)

	def withAuth(f: => String => Request[AnyContent] => Result) = {
		Security.Authenticated(username, onUnauthorized) { user =>
			Action(request => f(user)(request))
		}
	}
}