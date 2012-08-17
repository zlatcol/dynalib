package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.openid._
import play.api.libs.concurrent._
import scala.util.matching.Regex

object Auth extends Controller {
	
	def login = Action {
		Ok(views.html.login())
	}

	def logout = Action { implicit request =>
		Redirect(routes.Auth.login).withNewSession.flashing(
			"success" -> "You are now logged out."
		)
	}

	def openIDCallback = Action { implicit request =>
		AsyncResult(
			OpenID.verifiedId.extend( _.value match {
				case Redeemed(info) => {
					info.attributes.get("email").map {
						email => {
							val r = new Regex("(.*)@dynabyte.se$")
							email match {
								case r(user) => {
									println(user) // <- do some more fancy stuff
									Redirect(routes.DynaLib.index).withSession(Security.username -> email)
								}
								case _ => {
									Logger.error("Not a correct domain")
									Redirect(routes.Auth.login)
								}
							}
						}
					}.getOrElse {
						Logger.error("No email in openId response")
						Redirect(routes.Auth.login)
					}
				}
				case Thrown(t) => {
					Redirect(routes.Auth.login)
				}
			})
		)
	}

	def loginPost = Action { implicit request =>
		Form(single(
			"openid" -> nonEmptyText
		)).bindFromRequest.fold(
			error => {
				Logger.info("bad request " + error.toString)
				BadRequest(error.toString)
			},
			{
				case (openid) => 
					AsyncResult(
					    OpenID.redirectURL(openid, routes.Auth.openIDCallback.absoluteURL(),
					    	Seq("email" -> "http://schema.openid.net/contact/email")
					    ).extend(
					    	_.value match {
					    		case Redeemed(url) => Redirect(url)
					    		case Thrown(t) => Redirect(routes.Auth.login)
					    	}
					    )
					)
			}
		)
	}
}