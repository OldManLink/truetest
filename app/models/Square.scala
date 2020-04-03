package models

import helpers.Move
import helpers.Step
import play.api.libs.json.{Json, OFormat}

case class Square(rowIndex: Int, columnIndex: Int) {

  def allSteps(board: Board): List[Step] = {
    Move.validMoves.map(move => move.destination(board, this)).toList
  }

  def possibleSteps(board: Board): List[Step] = {
    val all = allSteps(board).filter(_.legal)
    all
  }

  def index(board: Board): Int = rowIndex * board.columnSize + columnIndex
}

object Square {

  def apply(desc: SquareDescription): Square = {
    Square(desc.row, desc.column)
  }

  implicit val squareFormat: OFormat[Square] = Json.format[Square]
}