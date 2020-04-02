package models

import play.api.libs.json.{Json, OFormat}

case class Tour(id: Int, name: String, squares: Seq[(Int, Int)])

object Tour {
  implicit val tourFormat: OFormat[Tour] = Json.format[Tour]
}