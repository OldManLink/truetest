package models

import helpers.Step
import helpers.Move._
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json

@RunWith(classOf[JUnitRunner])
class SquareSpec extends Specification {

  "Square" should {

    "be created from SquareDescription" in {
      //noinspection RedundantDefaultArgument
      Square(SquareDescription(1, 1)) must beEqualTo(Square(1, 1, visited = false))
    }

    "be marked as visited" in {
      Square(0,0).asVisited must beEqualTo(Square(0, 0, visited = true))
    }

    "correctly identify matching position" in {
      Square(0,0).asVisited.samePosition(Square(0,0)) must beTrue
      Square(0,1).samePosition(Square(0,0)) must beFalse
    }

    "correctly list all possible and impossible steps" in {
      Square(2, 2).allSteps(Board(BoardDescription(10, 10))) mustEqual
        Seq(
          Step(Some(Square(5, 2)),N),
          Step(Some(Square(4, 4)),NE),
          Step(Some(Square(2, 5)),E),
          Step(Some(Square(0, 4)),SE),
          Step(None, S),
          Step(Some(Square(0, 0)), SW),
          Step(None, W),
          Step(Some(Square(4, 0)),NW),
        )
    }

    "correctly list all possible steps" in {
      Square(1, 2).possibleSteps(Board(BoardDescription(10, 10))) mustEqual
        Seq(
          Step(Some(Square(4, 2)),N),
          Step(Some(Square(3, 4)),NE),
          Step(Some(Square(1, 5)),E),
          Step(Some(Square(3, 0)),NW),
        )
    }

    "correctly list all unvisited steps" in {
      val board = Board(BoardDescription(10, 10))
        .visit(Square(3,4))
        .visit(Square(3,0))
      Square(1, 2).unvisitedSteps(board) mustEqual
        Seq(
          Step(Some(Square(4, 2)),N),
          Step(Some(Square(1, 5)),E),
        )
    }

    "deserialise from Json" in {
      val square = Json.fromJson[Square](Json.parse("""{"rowIndex":0,"columnIndex":0,"visited":false}"""))
      square.get mustEqual Square(0,0)
    }

    "serialise to Json" in {
      Json.toJson(Square(0,0)).toString() must beEqualTo("""{"rowIndex":0,"columnIndex":0,"visited":false}""")
    }
  }
}
