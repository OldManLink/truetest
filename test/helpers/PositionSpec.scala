package helpers

import helpers.Move._
import models.{Square, Tour}
import models.Square.ORIGIN
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PositionSpec extends Specification {

  "Position" should {

    "convert itself into a Tour" in {
      val position = Position(Square(4, 2, 18, Nil), List(S, W, E, N, O))
      position.toTour(42, ORIGIN) must equalTo(Tour(42, 0, 0, List(N, E, W, S), (4, 2)))
    }
  }
}
