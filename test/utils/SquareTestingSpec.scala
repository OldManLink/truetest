package utils

import helpers.ObjectFactory.getTestBoard
import models.{Square, TourRequest}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SquareTestingSpec extends Specification with SquareTesting {

  "SquareTesting" should {

    "make a blocked square" in {
      blocked(0, 0, 0) must beEqualTo(Square(0, 0, 0, Nil))
    }

    "make a blocked version of a square" in {
      getTestBoard(TourRequest((10, 10), (4, 4), 42))
        .getSquare(4, 2).map(asBlocked) must beSome(Square(4, 2, 42, Nil))
    }
  }
}
