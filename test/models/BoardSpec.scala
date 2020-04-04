package models

import helpers.ObjectFactory.getOptimisedBoard
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import utils.SquareTesting

@RunWith(classOf[JUnitRunner])
class BoardSpec extends Specification with SquareTesting {

  "Board" should {

    "correctly return requested squares that exist" in {
      getOptimisedBoard(TourRequest((10, 10), (4, 2), 42))
        .getSquare(4, 2).map(asBlocked) must beSome(Square(4, 2, 42, Nil))
    }

    "correctly fail to return non-existent squares" in {
      getOptimisedBoard(TourRequest((5, 5), (4, 2), 42))
        .getSquare(5, 5) must beNone
    }

    "correctly return its squareCount" in {
      Board.EMPTY.squareCount must beEqualTo(0)
      getOptimisedBoard(TourRequest((5, 5), (4, 2), 42)).squareCount must beEqualTo(25)
      getOptimisedBoard(TourRequest((10, 10), (4, 2), 42)).squareCount must beEqualTo(100)
    }
  }
}
