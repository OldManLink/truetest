package models

case class Board(rows: Seq[Row]) {

  def getSquare(row: Int, column: Int): Option[Square] = rows.find(_.index == row).flatMap(_.getSquare(column))

  lazy val squareCount: Int = rows.size * rows.headOption.map(_.squares.size).getOrElse(0)

  def hasBlockedSquares: Boolean = rows.exists(_.hasBlockedSquares)

  def hasUnblockedSquares: Boolean = rows.exists(_.hasUnblockedSquares)

@Override
override def toString: String = s"""Board: {rows: ${rows.reverse.mkString("[\n  ", ",\n  ", "\n]")}}"""
}

object Board {

  val EMPTY: Board = Board(Nil)
}
