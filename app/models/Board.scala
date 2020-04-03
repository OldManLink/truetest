package models

import play.api.libs.json.{Json, OFormat}

case class Board(rows: Seq[Row]) {

  def columnSize: Int = rows.head.squares.size

  def squareCount: Int = rows.size * columnSize

  def getSquare(row: Int, column: Int): Option[Square] = {
    rows.find(_.index == row).flatMap(_.getSquare(column))
  }
}

object Board {

  def apply(desc: BoardDescription): Board =
    Board(
      0 until desc.rows map { rowIndex =>
        Row(
          rowIndex, 0 until desc.columns map { colIndex =>
            Square(SquareDescription(rowIndex, colIndex))
          })
      })

  implicit val boardFormat: OFormat[Board] = Json.format[Board]
}