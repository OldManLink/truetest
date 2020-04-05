package models

import helpers.Step

case class Square(rowIndex: Int, columnIndex: Int, boardIndex: Int, possibleSteps: Seq[Step]) {

  lazy val isBlocked: Boolean = possibleSteps == Nil

  def asTuple: (Int, Int) = (rowIndex, columnIndex)

  @Override
  override def toString: String =
    s"""{$rowIndex, $columnIndex, index: $boardIndex steps: ${
      possibleSteps.mkString("[", ",", "]")}}"""
}

object Square {

  val ORIGIN: Square = Square(0, 0, 0, Nil)
}
