package models.wine

// http://winefolly.com/review/wine-characteristics/
// http://en.wikipedia.org/wiki/Wine

case class Wine(color: String, name: String, year: Int, denomination: String, country: String, description: String, comments: String)

object Wine {

  var wines = Set(
    Wine(
      "Red",
      "Castell del Remei",
      2009,
      "Costers del Segre",
      "Spain",
      "Selecció de Merlot, Cabernet Sauvingnon, Ull de LLebre i Garnatxa. Criat durant 12 mesos em bótes de roure Americá i Francés. Ric i ampli en aromes i amb tanins sedosos.",
      "Very very nice"),
    Wine(
      "Red",
      "Legaris Roble",
      2013,
      "Ribera del Duero",
      "Spain",
      "Vino con aromas intensos de fruta roja y ligeras notas a vanilla de su permanencia de 3 meses en barrica de roble americano. En boca es equilibrado y saboroso.",
      "I think this is the wine Joanet likes.")

  )

  def findAll = wines.toList.sortBy(_.name)

}