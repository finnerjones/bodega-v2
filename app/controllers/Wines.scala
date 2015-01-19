package controllers

import models.wine.Wine
import play.api.mvc.{Action, Controller}

/**
 * Created by finner on 19/01/2015.
 */
object Wines extends Controller {

    def list = Action { implicit request =>
      val wines = Wine.findAll
      Ok(views.html.wines.list(wines))

    }
}
