package models

import play.api.libs.json.{Json, OFormat}

case class BoardDescription(rows: Int, columns: Int) {

  def asTuple: (Int, Int) = (rows, columns)
}

object BoardDescription {

  def apply(tuple: (Int, Int)): BoardDescription = BoardDescription(tuple._1, tuple._2)

  implicit val boardFormat: OFormat[BoardDescription] = Json.format[BoardDescription]
}
