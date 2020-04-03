package helpers

import models.Square

case class Step(square: Option[Square], move: Move) {

  def legal: Boolean = square.isDefined

}
