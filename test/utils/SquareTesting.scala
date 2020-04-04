package utils

import models.Square

trait SquareTesting {

  def blocked(row: Int, col: Int, boardIndex: Int): Square = Square(row, col, boardIndex, Nil)

  def asBlocked(sq: Square): Square = blocked(sq.rowIndex, sq.columnIndex, sq.boardIndex)
}
