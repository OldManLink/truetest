package models

import helpers.ObjectFactory.{SquareResolver, getOptimisedBoard}
import helpers.{Move, Step}
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import utils.SquareTesting

@RunWith(classOf[JUnitRunner])
class RowSpec extends Specification with SquareTesting with Mockito {

  val resolver: SquareResolver = mock[SquareResolver]
  val blockedSquare: Square = blocked(0, 0, 0)
  val unblockedSquare: Square = Square(1, 1, 1, Seq(Step(Move.NW, resolver)))

  "Row" should {

    "correctly return requested squares that exist" in {
      val testBoard = getOptimisedBoard(TourRequest((5, 5), (0, 0), 42))
      testBoard.rows(4).getSquare(2).map(asBlocked) must beSome(Square(4, 2, 22, Nil))
    }

    "correctly fail to return non-existent squares" in {
      val testBoard = getOptimisedBoard(TourRequest((5, 5), (0, 0), 42))
      testBoard.rows(3).getSquare(5) must beNone
    }

    "correctly answer if it has blockedSquares" in {
      Row(42, Seq(blockedSquare, blockedSquare)).hasBlockedSquares must beTrue
    }
  }
}
