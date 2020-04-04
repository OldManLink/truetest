package helpers

import helpers.ObjectFactory.SquareResolver
import models.Square

case class Step(move: Move, squareResolver: SquareResolver) {

  def unVisitedSize(visited: Seq[Int]): Int = square.possibleSteps
    .filterNot(step => visited.contains(step.square.boardIndex)).size

  lazy val square: Square = squareResolver.resolve

  @Override
  override def toString: String = s"{$move, ${squareResolver}}"
}