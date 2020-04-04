package models

case class Row(index: Int, squares: Seq[Square]) {

  def getSquare(column: Int): Option[Square] = squares.find(_.columnIndex == column)

  def hasBlockedSquares: Boolean = squares.exists(_.isBlocked)

  def hasUnblockedSquares: Boolean = squares.exists(!_.isBlocked)

  @Override
  override def toString: String = s"""Row: {index: $index, squares: ${squares.mkString("[\n    ", ",\n    ", "\n    ]")}\n  }"""
}
