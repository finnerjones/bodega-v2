package controllers

import models.Wine
import play.api.mvc.{Action, Controller}

/**
 * Created by finner on 19/01/2015.
 */
object Wines extends Controller {

    def list = Action { implicit request =>
      val wines = Wine.findAll
      Ok(views.html.wines.list(wines))

    }


  def show(id: Int) = Action { implicit request =>
    Wine.findById(id).map { wine =>
      Ok(views.html.wines.details(wine))
    }.getOrElse(NotFound)

  }

}
