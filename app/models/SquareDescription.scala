package models

import play.api.libs.json.{Json, OFormat}

case class SquareDescription(row: Int, column: Int){

  def asTuple: (Int, Int) = (row, column)
}

object SquareDescription {

  def apply(tuple: (Int, Int)): SquareDescription = SquareDescription(tuple._1, tuple._2)

  implicit val boardFormat: OFormat[SquareDescription] = Json.format[SquareDescription]
}
