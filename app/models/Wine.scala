package models

import java.util.Date

import anorm.{Row, SqlQuery, SQL}
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
                wineCeller: Option[String],
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
        row[Option[String]]("wine_celler"),
        row[Option[String]]("wine_denom_origin"),
        row[Option[String]]("wine_vender"),
        row[Option[Double]]("wine_alcohol"),
        row[Option[Date]]("wine_date_purchased"),
        row[Option[Date]]("wine_date_opened"),
        row[Option[Date]]("wine_date_inserted"),
        row[Option[Date]]("wine_date_last_modified"),
        row[Option[String]]("wine_comments"))).toList
  }

  def findById(id:Long):Wine =
    DB.withConnection {
      implicit connection =>
        println("[findById()]  ****   select * from wine where wine_id = " + id)
        val findWineSQL = SQL("""select * from wine where wine_id = {id}""").on("id" -> id)

        val wines:List[Wine] = findWineSQL().collect {
          case Row(
              Some(wineId:Long),
              Some(wineName:String),
              Some(wineType:String),
              Some(wineCountry:String),
              Some(wineDescription:Option[String]),
              Some(wineYear:Int),
              Some(wineGrapes:Option[String]),
              Some(winePrice:Option[Double]),
              Some(wineCeller:Option[String]),
              Some(wineDenomOrigin:Option[String]),
              Some(wineVender:Option[String]),
              Some(wineAlcohol:Option[Double]),
              Some(wineDatePurchased:Option[Date]),
              Some(wineDateOpened:Option[Date]),
              Some(wineDateInserted:Option[Date]),
              Some(wineDateLastModified:Option[Date]),
              Some(wineComments:Option[String])) =>
                Wine(wineId,wineName,wineType,wineCountry,
                  wineDescription,wineYear,wineGrapes,winePrice,wineCeller,
                  wineDenomOrigin,wineVender,wineAlcohol,wineDatePurchased,
                  wineDateOpened,wineDateInserted,wineDateLastModified,wineComments)
      }.toList
      wines.head

  }



  def add(wine:Wine) : Boolean =

    DB.withConnection { implicit connection =>
      val addedRows = SQL("""insert into wine(wine_name, wine_type, wine_country,
                            wine_description, wine_year, wine_grapes, wine_price, wine_celler,
                            wine_denom_origin, wine_vender, wine_alcohol, wine_date_purchased,
                            wine_date_opened, wine_date_inserted, wine_date_last_modified, wine_comments
                          ) values ({wineName},{wineType},{wineCountry},
                                  {wineDescription},{wineYear},{wineGrapes},{winePrice},{wineCeller},
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
          "wineCeller" -> wine.wineCeller,
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
      println("[add()]  ****   row added a wine, result = " + (addedRows == 1))
      addedRows == 1
    }

}