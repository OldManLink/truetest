package helpers

import helpers.Move._
import models.Square.ORIGIN
import models.Tour
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PositionSpec extends Specification {

  "Position" should {

    "convert itself into a Tour" in {
      val position = Position(ORIGIN, List(S, W, E, N))
      position.toTour(42, ORIGIN) must equalTo(Tour(42, 0, 0, List(N, E, W, S)))
    }
  }
}
