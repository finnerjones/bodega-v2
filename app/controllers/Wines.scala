package controllers

import anorm.{Pk, NotAssigned}
import models.Wine
import play.api.mvc.{Action, Controller}
import play.api.data.format.Formats._
import play.api.data.Form
import play.api.data.Forms.{mapping, number, nonEmptyText, text, date, of, ignored}
import play.api.i18n.Messages
import play.api.mvc.Flash

/**
 * Created by finner on 19/01/2015.
 */
object Wines extends Controller {


  private val wineForm: Form[Wine] = Form(
    mapping(
      "wineId" -> ignored(NotAssigned:Pk[Int]),
      "wineName" -> nonEmptyText,
      "wineType" -> nonEmptyText,
      "wineCountry" -> nonEmptyText,
      "wineDescription" -> text,
      "wineYear" -> number.verifying(_ > 1000),
      "wineGrapes" -> text,
      "winePrice" -> of(doubleFormat),
      "wineCeller" -> text,
      "wineDenomOrigin" -> text,
      "wineVender" -> text,
      "wineAlcohol" -> of(doubleFormat),
      "wineDatePurchased" -> date,
      "wineDateOpened" -> date,
      "wineDateInserted" -> date,
      "wineDateLastModified" -> date,
      "wineComments" -> text
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

  def show(id: Int) = Action { implicit request =>
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
        Redirect(routes.Wines.show(newWine.wineId.get)).flashing("success" -> message)
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
