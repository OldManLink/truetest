package helpers

import models.{Board, BoardDescription, Square, Tour}
import Move._
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PositionSpec extends Specification {

  "Position" should {

    "know when it is done" in {
      val board0 = Board(BoardDescription(2, 2))
      val board1 = Board(BoardDescription(2, 2)).visit(Square(0,0))
      val board2 = Board(BoardDescription(2, 2)).visit(Square(0,0)).visit(Square(0,1))
      val board3 = Board(BoardDescription(2, 2)).visit(Square(0,0)).visit(Square(0,1)).visit(Square(1,0))
      val board4 = Board(BoardDescription(2, 2)).visit(Square(0,0)).visit(Square(0,1)).visit(Square(1,0)).visit(Square(1,1))

      Position(Square(0,0), board0, Nil ).done must beFalse
      Position(Square(0,0), board1, Nil ).done must beFalse
      Position(Square(0,0), board2, Nil ).done must beFalse
      Position(Square(0,0), board3, Nil ).done must beFalse
      Position(Square(0,0), board4, Nil ).done must beTrue
    }

    "convert itself into a Tour" in {
      val board = Board(BoardDescription(2, 2))
      val position = Position(Square(0,0), board, List(S, W, N))
      position.toTour(42, Square(0,1)) must equalTo(Tour(42, 0, 1, List(N, W, S)))

    }
  }
}
