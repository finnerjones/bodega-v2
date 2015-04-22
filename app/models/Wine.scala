package models

import java.util.Date

import anorm.{Row, SqlQuery, SQL, Pk}
import play.api.db.DB
import play.api.Play.current

// http://winefolly.com/review/wine-characteristics/
// http://en.wikipedia.org/wiki/Wine

case class Wine(wineId: Pk[Int],
                wineName: String,
                wineType: String,
                wineCountry: String,
                wineDescription: String,
                wineYear: Int,
                wineGrapes: String,
                winePrice: Double,
                wineCeller: String,
                wineDenomOrigin: String,
                wineVender: String,
                wineAlcohol: Double,
                wineDatePurchased: Date,
                wineDateOpened: Date,
                wineDateInserted: Date,
                wineDateLastModified: Date,
                wineComments: String)

object Wine {

  def findAll : List[Wine] = DB.withConnection {
    implicit connection =>
      val sql:SqlQuery = SQL("SELECT * FROM wine")
      sql().map( row =>
      Wine(
        row[Pk[Int]]("wine_id"),
        row[String]("wine_name"),
        row[String]("wine_type"),
        row[String]("wine_country"),
        row[String]("wine_description"),
        row[Int]("wine_year"),
        row[String]("wine_grapes"),
        row[Double]("wine_price"),
        row[String]("wine_celler"),
        row[String]("wine_denom_origin"),
        row[String]("wine_vender"),
        row[Double]("wine_alcohol"),
        row[Date]("wine_date_purchased"),
        row[Date]("wine_date_opened"),
        row[Date]("wine_date_inserted"),
        row[Date]("wine_date_last_modified"),
        row[String]("wine_comments"))).toList
  }

  def findById(id:Int):Wine =
    DB.withConnection {
      implicit connection =>
        println("[findById()]  ****   select * from wine where id = " + id)
        val findWineSQL = SQL("""select * from wine where id = {id}""").on("id" -> id)

        val wines:List[Wine] = findWineSQL().collect {
          case Row(
              Some(wineId:Pk[Int]),
              Some(wineName:String),
              Some(wineType:String),
              Some(wineCountry:String),
              Some(wineDescription:String),
              Some(wineYear:Int),
              Some(wineGrapes:String),
              Some(winePrice:Double),
              Some(wineCeller:String),
              Some(wineDenomOrigin:String),
              Some(wineVender:String),
              Some(wineAlcohol:Double),
              Some(wineDatePurchased:Date),
              Some(wineDateOpened:Date),
              Some(wineDateInserted:Date),
              Some(wineDateLastModified:Date),
              Some(wineComments:String)) =>
                Wine(wineId,wineName,wineType,wineCountry,
                  wineDescription,wineYear,wineGrapes,winePrice,wineCeller,
                  wineDenomOrigin,wineVender,wineAlcohol,wineDatePurchased,
                  wineDateOpened,wineDateInserted,wineDateLastModified,wineComments)
      }.toList
      wines.head

  }


  def add(wine:Wine) : Boolean =

    DB.withConnection { implicit connection =>
      val addedRows = SQL("""insert into wine values ({wineName},{wineType},{wineCountry},
                            |                  {wineDescription},{wineYear},{wineGrapes},{winePrice},{wineCeller},
                            |                  {wineDenomOrigin},{wineVender},{wineAlcohol},{wineDatePurchased},
                            |                  {wineDateOpened},{wineDateInserted},{wineDateLastModified},{wineComments})""")
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
        executeInsert()
      println("[add()]  ****   row added for id " + wine.wineId + " result = " + (addedRows == 1))
      addedRows == 1
    }

}