package models

import anorm.{Row, SqlQuery, SQL}
import play.api.db.DB
import play.api.Play.current

// http://winefolly.com/review/wine-characteristics/
// http://en.wikipedia.org/wiki/Wine

case class Wine(id: Int, color: String, name: String, year: Int, denomination: String, country: String, description: String, comments: String)

object Wine {

  var wines = Set(
    Wine(
      1,
      "Red",
      "Castell del Remei",
      2009,
      "Costers del Segre",
      "Spain",
      "Selecció de Merlot, Cabernet Sauvingnon, Ull de LLebre i Garnatxa. Criat durant 12 mesos em bótes de roure Americá i Francés. Ric i ampli en aromes i amb tanins sedosos.",
      "Very very nice"),
    Wine(
      2,
      "Red",
      "Legaris Roble",
      2013,
      "Ribera del Duero",
      "Spain",
      "Vino con aromas intensos de fruta roja y ligeras notas a vanilla de su permanencia de 3 meses en barrica de roble americano. En boca es equilibrado y saboroso.",
      "I think this is the wine Joanet likes.")

  )

  def findAllHardcoded = wines.toList.sortBy(_.name)

  def findAll : List[Wine] = DB.withConnection {
    implicit connection =>
      val sql:SqlQuery = SQL("SELECT * FROM wine")
      sql().map( row =>
      Wine(
        row[Int]("id"),
        row[String]("color"),
        row[String]("name"),
        row[Int]("year"),
        row[String]("denomination"),
        row[String]("country"),
        row[String]("description"),
        row[String]("comments"))).toList

  }



  def findByIdHarcoded(id: Int) = {
    val fwine = wines.find(_.id == id)
    if (fwine.isDefined)
      System.out.println("  *****************  " + fwine.get.name)
    else
      System.out.println("     Found Nothing for ........")
    fwine
  }


  def findById(id:Int):Wine =
    DB.withConnection {
      implicit connection =>
        println("[findById()]  ****   select * from wine where id = " + id)
        val findWineSQL = SQL("""select * from wine where id = {id}""").on("id" -> id)



        val wines:List[Wine] = findWineSQL().collect {
          case Row(
              Some(id:Int),
              Some(color:String),
              Some(name:String),
              Some(year:Int),
              Some(denomination:String),
              Some(country:String),
              Some(description:String),
              Some(comments:String)) =>
            Wine(id,color,name,year,denomination,country,description,comments)
      }.toList
      wines.head

  }




def addHardcoded(wine:Wine) {
    wines = wines + wine
  }


  def add(wine:Wine) : Boolean =

    DB.withConnection { implicit connection =>
      val addedRows = SQL("""insert into wine values ({id}, {color}, {name}, {year}, {denomination}, {country}, {description},{comments})""")
        .on(
          "id" -> wine.id,
          "color" -> wine.color,
          "name" -> wine.name,
          "year" -> wine.year,
          "denomination" -> wine.denomination,
          "country" -> wine.country,
          "description" -> wine.description,
          "comments" -> wine.comments
        ).
        executeUpdate()
      println("[add()]  ****   row added for id " + wine.id + " result = " + addedRows == 1)
      addedRows == 1
    }

}