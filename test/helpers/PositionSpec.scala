package helpers

import models.{Square, Tour}
import Move._
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PositionSpec extends Specification {

  "Position" should {

    "convert itself into a Tour" in {
      val position = Position(Square(0,0), List(S, W, N))
      position.toTour(42, Square(0,1)) must equalTo(Tour(42, 0, 1, List(N, W, S)))

    }
  }
}
