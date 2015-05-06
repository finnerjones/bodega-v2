package models

import java.util.Date

import anorm.{SimpleSql, Row, SqlQuery, SQL}
import play.api.db.DB
import play.api.Play.current

// http://winefolly.com/review/wine-characteristics/
// http://en.wikipedia.org/wiki/Wine

// The Model matching the WINE table in Database
case class Wine(wineId: Long,
                wineName: String,
                wineType: String,
                wineCountry: String,
                wineDescription: Option[String],
                wineYear: Int,
                wineGrapes: Option[String],
                winePrice: Option[Double],
                wineCellar: Option[String],
                wineDenomOrigin: Option[String],
                wineVender: Option[String],
                wineAlcohol: Option[Double],
                wineDatePurchased: Option[Date],
                wineDateOpened: Option[Date],
                wineDateInserted: Option[Date],
                wineDateLastModified: Option[Date],
                wineComments: Option[String])

object Wine {


  /*
    This is an implementation using the Anorm Stream API
   */
  def findAll : List[Wine] = DB.withConnection {
    implicit connection =>
      val sql:SqlQuery = SQL("SELECT * FROM wine")
      sql().map( row =>
      Wine(
        row[Long]("wine_id"),
        row[String]("wine_name"),
        row[String]("wine_type"),
        row[String]("wine_country"),
        row[Option[String]]("wine_description"),
        row[Int]("wine_year"),
        row[Option[String]]("wine_grapes"),
        row[Option[Double]]("wine_price"),
        row[Option[String]]("wine_cellar"),
        row[Option[String]]("wine_denom_origin"),
        row[Option[String]]("wine_vender"),
        row[Option[Double]]("wine_alcohol"),
        row[Option[Date]]("wine_date_purchased"),
        row[Option[Date]]("wine_date_opened"),
        row[Option[Date]]("wine_date_inserted"),
        row[Option[Date]]("wine_date_last_modified"),
        row[Option[String]]("wine_comments"))).toList
  }


  /*
  This is an implementation using map from the Anorm Stream API
 */
  def findById(id:Long) : Wine = DB.withConnection {
    implicit connection =>
      val sql:SimpleSql[Row] = SQL("SELECT * FROM wine where wine_id = {id}").on("id" -> id)
      sql().map( row =>
        Wine(
          row[Long]("wine_id"),
          row[String]("wine_name"),
          row[String]("wine_type"),
          row[String]("wine_country"),
          row[Option[String]]("wine_description"),
          row[Int]("wine_year"),
          row[Option[String]]("wine_grapes"),
          row[Option[Double]]("wine_price"),
          row[Option[String]]("wine_cellar"),
          row[Option[String]]("wine_denom_origin"),
          row[Option[String]]("wine_vender"),
          row[Option[Double]]("wine_alcohol"),
          row[Option[Date]]("wine_date_purchased"),
          row[Option[Date]]("wine_date_opened"),
          row[Option[Date]]("wine_date_inserted"),
          row[Option[Date]]("wine_date_last_modified"),
          row[Option[String]]("wine_comments"))).toList.head
  }



  /*
    This does not work because the SELECT returns nothing
   */
  def findByIdCollect(id:Long):Wine =
    DB.withConnection {
      implicit connection =>
        val findWineSQL:SimpleSql[Row] = SQL("SELECT * FROM wine where wine_id = {id}").on("id" -> id)

        val wines:List[Wine] = findWineSQL().collect {
          //case _ => Wine(id,"name","type","country",null,2010,null,null,null,null,null,null,null,null,null,null,null)
          case Row(
              wineId:Long,
              wineName:String,
              wineType:String,
              wineCountry:String,
              Some(wineDescription:String),
              wineYear:Int,
              Some(wineGrapes:String),
              Some(winePrice:Double),
              Some(wineCellar:String),
              Some(wineDenomOrigin:String),
              Some(wineVender:String),
              Some(wineAlcohol:Double),
              Some(wineDatePurchased:Date),
              Some(wineDateOpened:Date),
              Some(wineDateInserted:Date),
              Some(wineDateLastModified:Date),
              Some(wineComments:String)) =>
                Wine(wineId,wineName,wineType,wineCountry,
                  Some(wineDescription),wineYear,Some(wineGrapes),Some(winePrice),Some(wineCellar),
                  Some(wineDenomOrigin),Some(wineVender),Some(wineAlcohol),Some(wineDatePurchased),
                  Some(wineDateOpened),Some(wineDateInserted),Some(wineDateLastModified),Some(wineComments))
      }.toList
      wines.head

  }



  def add(wine:Wine) : Boolean =

    DB.withConnection { implicit connection =>
      val addedRows = SQL("""insert into wine(wine_name, wine_type, wine_country,
                            wine_description, wine_year, wine_grapes, wine_price, wine_cellar,
                            wine_denom_origin, wine_vender, wine_alcohol, wine_date_purchased,
                            wine_date_opened, wine_date_inserted, wine_date_last_modified, wine_comments
                          ) values ({wineName},{wineType},{wineCountry},
                                  {wineDescription},{wineYear},{wineGrapes},{winePrice},{wineCellar},
                                  {wineDenomOrigin},{wineVender},{wineAlcohol},{wineDatePurchased},
                                  {wineDateOpened},{wineDateInserted},{wineDateLastModified},{wineComments})""")
        .on(
          "wineName" -> wine.wineName,
          "wineType" -> wine.wineType,
          "wineCountry" -> wine.wineCountry,
          "wineDescription" -> wine.wineDescription,
          "wineYear" -> wine.wineYear,
          "wineGrapes" -> wine.wineGrapes,
          "winePrice" -> wine.winePrice,
          "wineCellar" -> wine.wineCellar,
          "wineDenomOrigin" -> wine.wineDenomOrigin,
          "wineVender" -> wine.wineVender,
          "wineAlcohol" -> wine.wineAlcohol,
          "wineDatePurchased" -> wine.wineDatePurchased,
          "wineDateOpened" -> wine.wineDateOpened,
          "wineDateInserted" -> wine.wineDateInserted,
          "wineDateLastModified" -> wine.wineDateLastModified,
          "wineComments" -> wine.wineComments
        ).
        executeInsert()   // executeInsert() should auto generate the ID if the PK is Long
      addedRows == 1
    }

  def delete(id:Long): Boolean =
    DB.withConnection { implicit connection =>
      val updatedRows = SQL("DELETE FROM wine WHERE wine_id = {id}").on("id" -> id).executeUpdate()
      updatedRows == 1
    }
}