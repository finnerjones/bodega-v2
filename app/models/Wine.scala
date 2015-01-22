package models

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

  def findAll = wines.toList.sortBy(_.name)

  def findById(id: Int) = {
    val fwine = wines.find(_.id == id)
    if (fwine.isDefined)
      System.out.println("  *****************  " + fwine.get.name)
    else
      System.out.println("     Found Nothing for ........")
    fwine
  }


  def add(wine:Wine) {
    wines = wines + wine
  }
}