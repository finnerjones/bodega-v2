package controllers

import anorm.{Pk, NotAssigned}
import models.Wine
import play.api.mvc.{Action, Controller}
import play.api.data.format.Formats._
import play.api.data.Form
import play.api.data.Forms.{mapping, number, longNumber, nonEmptyText, text, date, of, ignored, optional}
import play.api.i18n.Messages
import play.api.mvc.Flash

/**
 * Created by finner on 19/01/2015.
 */
object Wines extends Controller {


  private val addWineForm: Form[Wine] = Form(
    mapping(
      "wineId" -> ignored(0L),
      "wineName" -> nonEmptyText,
      "wineType" -> nonEmptyText,
      "wineCountry" -> nonEmptyText,
      "wineDescription" -> optional(text),
      "wineYear" -> number.verifying(_ > 1000),
      "wineGrapes" -> optional(text),
      "winePrice" -> optional(of(doubleFormat)),
      "wineCellar" -> optional(text),
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

  private val updateWineForm: Form[Wine] = Form(
    mapping(
      "wineId" -> longNumber,
      "wineName" -> nonEmptyText,
      "wineType" -> nonEmptyText,
      "wineCountry" -> nonEmptyText,
      "wineDescription" -> optional(text),
      "wineYear" -> number.verifying(_ > 1000),
      "wineGrapes" -> optional(text),
      "winePrice" -> optional(of(doubleFormat)),
      "wineCellar" -> optional(text),
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


  def show(id: Long) = Action { implicit request =>
    val wine = Wine.findById(id)
    Ok(views.html.wines.details(wine))
  }


  def edit(id:Long) = Action { implicit request =>
    val wine = Wine.findById(id)
    val wineToEdit = updateWineForm.fill(wine)
    wineToEdit.fold(
      hasErrors = { form =>
        Redirect(routes.Wines.list()).flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
      },
      success = { newWine =>
        Ok(views.html.wines.updateWine(wineToEdit))
      }
    )

  }

  def update = Action { implicit request =>
    val wineToUpdate = updateWineForm.bindFromRequest()
    wineToUpdate.fold(
      hasErrors = { form =>
        Redirect(routes.Wines.update()).flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
      },
      success = { updateWine =>
        Wine.update(updateWine)
        val message = Messages("wines.update.success", updateWine.wineName)
        Redirect(routes.Wines.list()).flashing("success" -> message)
      }
    )
  }


  def save = Action { implicit request =>
    val newWine = addWineForm.bindFromRequest()

    newWine.fold(
      hasErrors = { form =>
        Redirect(routes.Wines.newWine()).flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
      },
      success = { newWine =>
        Wine.add(newWine)
        val message = Messages("wines.new.success", newWine.wineName)
        Redirect(routes.Wines.list()).flashing("success" -> message)
      }
    )
  }

  def newWine = Action { implicit request =>
    val form = if (flash.get("error").isDefined)
      addWineForm.bind(flash.data)
    else {
      addWineForm
    }

    val years = Wine.years()
    Ok(views.html.wines.addWine(form, years))
  }


  def deleteWine(id:Long) = Action { implicit request =>
    val wine:Wine = Wine.findById(id)
    val success = Wine.delete(id)
    if (success) {
      val message = Messages("wines.delete.success", wine.wineName)
      Redirect(routes.Wines.list()).flashing("success" -> message)
    } else {
      val message = Messages("wines.delete.error", wine.wineName)
      Redirect(routes.Wines.list()).flashing("success" -> message)
    }
  }

}
