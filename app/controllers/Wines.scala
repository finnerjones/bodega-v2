package controllers

import anorm.{Pk, NotAssigned}
import models.Wine
import play.api.mvc.{Action, Controller}
import play.api.data.format.Formats._
import play.api.data.Form
import play.api.data.Forms.{mapping, number, nonEmptyText, text, date, of, ignored, optional}
import play.api.i18n.Messages
import play.api.mvc.Flash

/**
 * Created by finner on 19/01/2015.
 */
object Wines extends Controller {


  private val wineForm: Form[Wine] = Form(
    mapping(
      "wineId" -> ignored(0L),
      "wineName" -> nonEmptyText,
      "wineType" -> nonEmptyText,
      "wineCountry" -> nonEmptyText,
      "wineDescription" -> optional(text),
      "wineYear" -> number.verifying(_ > 1000),
      "wineGrapes" -> optional(text),
      "winePrice" -> optional(of(doubleFormat)),
      "wineCeller" -> optional(text),
      "wineDenomOrigin" -> optional(text),
      "wineVender" -> optional(text),
      "wineAlcohol" -> optional(of(doubleFormat)),
      "wineDatePurchased" -> optional(date),
      "wineDateOpened" -> optional(date),
      "wineDateInserted" -> optional(date),
      "wineDateLastModified" -> optional(date),
      "wineComments" -> optional(text)
    )(Wine.apply)(Wine.unapply)
  )


  def list = Action { implicit request =>
    val wines = Wine.findAll
    Ok(views.html.wines.list(wines))
  }


  def catalog = Action { implicit request =>
    val wines = Wine.findAll
    Ok(views.html.wines.catalog(wines))
  }

  def show(id: Long) = Action { implicit request =>
    println(" [show()] *****   findById for id: " + id)
    val wine = Wine.findById(id)
    Ok(views.html.wines.details(wine))
  }

  def save = Action { implicit request =>
    val newWineForm = wineForm.bindFromRequest()

    newWineForm.fold(
      hasErrors = { form =>
        Redirect(routes.Wines.newWine()).flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
      },
      success = { newWine =>
        Wine.add(newWine)
        val message = Messages("wines.new.success", newWine.wineName)
        Redirect(routes.Wines.show(newWine.wineId)).flashing("success" -> message)
      }
    )
  }

  def newWine = Action { implicit reques =>
    val form = if (flash.get("error").isDefined)
      wineForm.bind(flash.data)
    else
      wineForm

    Ok(views.html.wines.editWine(form))
  }

}
