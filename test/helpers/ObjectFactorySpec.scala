package helpers

import helpers.ObjectFactory.{getOptimisedBoard, getTestBoard}
import models.TourRequest.ORIGIN
import models.{Board, Row, Square, TourChunkRequest, TourRequest}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ObjectFactorySpec extends Specification {

  "ObjectFactory" should {

    "create an empty board" in {
      getTestBoard(TourRequest(ORIGIN)) must beEqualTo(Board.EMPTY)
    }

    "create a board with a single square" in {
      getTestBoard(TourRequest((1, 1))) must beEqualTo(Board(Seq(Row(0, Seq(Square(0, 0, 0, Nil))))))
    }

    "create the boards with only blocked squares" in {
      getTestBoard(TourRequest((1, 1))).hasUnblockedSquares must beFalse
      getTestBoard(TourRequest((1, 2))).hasUnblockedSquares must beFalse
      getTestBoard(TourRequest((1, 3))).hasUnblockedSquares must beFalse
      getTestBoard(TourRequest((2, 2))).hasUnblockedSquares must beFalse
      getTestBoard(TourRequest((2, 3))).hasUnblockedSquares must beFalse
      getTestBoard(TourRequest((3, 1))).hasUnblockedSquares must beFalse
      getTestBoard(TourRequest((3, 2))).hasUnblockedSquares must beFalse

    }

    "create some boards with automatically blocked squares" in {
      getTestBoard(TourRequest((1, 1))).hasBlockedSquares must beTrue
      getTestBoard(TourRequest((2, 2))).hasBlockedSquares must beTrue
      getTestBoard(TourRequest((3, 3))).hasBlockedSquares must beTrue
      getTestBoard(TourRequest((3, 4))).hasBlockedSquares must beTrue
      getTestBoard(TourRequest((4, 3))).hasBlockedSquares must beTrue

    }

    "create some boards with no blocked squares" in {
      getTestBoard(TourRequest((4, 4))).hasBlockedSquares must beFalse
      getTestBoard(TourRequest((5, 5))).hasBlockedSquares must beFalse
      getTestBoard(TourRequest((6, 6))).hasBlockedSquares must beFalse
      getTestBoard(TourRequest((7, 7))).hasBlockedSquares must beFalse
      getTestBoard(TourRequest((8, 8))).hasBlockedSquares must beFalse
      getTestBoard(TourRequest((9, 9))).hasBlockedSquares must beFalse
      getTestBoard(TourRequest((10, 10))).hasBlockedSquares must beFalse
    }

    "create some boards with no blocked squares using the new chunk API" in {
      getOptimisedBoard(TourChunkRequest(TourRequest((5, 5)))).hasBlockedSquares must beFalse
      getOptimisedBoard(TourChunkRequest(TourRequest((10, 10)))).hasBlockedSquares must beFalse
    }

    "display a cool visualisation of a minimal board" in {
      ObjectFactory.boardFactory(TourRequest((3, 3))).toString
        .mustEqual("""{rows: [
                     |    {index: 0: squares: [{0, 0},{0, 1},{0, 2}]},
                     |    {index: 1: squares: [{1, 0},{1, 1},{1, 2}]},
                     |    {index: 2: squares: [{2, 0},{2, 1},{2, 2}]}]}""".stripMargin)
    }

    "display a cool visualisation when this test is enabled" in {
      getTestBoard(TourRequest((4, 4))) must beEqualTo(Board.EMPTY)
    }.pendingUntilFixed("(** disabled **)")
  }
}
