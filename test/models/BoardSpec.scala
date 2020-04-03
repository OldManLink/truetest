package models

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json

@RunWith(classOf[JUnitRunner])
class BoardSpec extends Specification {

  "Board" should {

    "be created from BoardDescription" in {
      Board(BoardDescription(1, 1)) must beEqualTo(Board(Seq(Row(0, Seq(Square(0, 0))))))
      Board(BoardDescription(2, 1)) must beEqualTo(Board(Seq(Row(0, Seq(Square(0, 0))),Row(1, Seq(Square(1, 0))))))
      Board(BoardDescription(1, 2)) must beEqualTo(Board(Seq(Row(0, Seq(Square(0, 0),Square(0, 1))))))
    }

    "correctly report when all squares are visited or not" in {
      Board(BoardDescription(1, 1)).allVisited must beFalse
      Board(BoardDescription(2, 1)).allVisited must beFalse
      Board(BoardDescription(1, 2)).allVisited must beFalse

      Board(Seq(Row(0, Seq(Square(0, 0, visited = true)))))
        .allVisited must beTrue
      Board(Seq(Row(0, Seq(Square(0, 0, visited = true))),Row(1, Seq(Square(1, 0, visited = true)))))
        .allVisited must beTrue
      Board(Seq(Row(0, Seq(Square(0, 0, visited = true),Square(0, 1, visited = true)))))
        .allVisited must beTrue

      Board(Seq(Row(0, Seq(Square(0, 0),Square(0, 1, visited = true)))))
        .allVisited must beFalse
      Board(Seq(Row(0, Seq(Square(0, 0, visited = true),Square(0, 1)))))
        .allVisited must beFalse
    }

    "correctly return requested squares that exist" in {
      Board(BoardDescription(10, 10)).getSquare(5, 5) must beSome(Square(5, 5))
    }

    "correctly fail to return non-existent squares" in {
      Board(BoardDescription(5, 5)).getSquare(5, 5) must beNone
    }

    "mark squares as visited" in {
      Board(BoardDescription(5, 5)).visit(Square(2, 2)).getSquare(2, 2) must beSome(Square(2, 2, visited = true))
    }

    "correctly fail to mark non-existent squares as visited" in {
      Board(BoardDescription(2, 2)).visit(Square(5, 5)) must throwA[IndexOutOfBoundsException]("row/column")
    }

    "deserialise from Json" in {
      val board = Json.fromJson[Board](Json.parse("""{"rows":[{"index":0,"squares":[{"rowIndex":0,"columnIndex":0,"visited":false}]}]}"""))
      board.get mustEqual(Board(BoardDescription(1, 1)))
    }

    "serialise to Json" in {
      Json.toJson(Board(BoardDescription(1, 1))).toString() must beEqualTo("""{"rows":[{"index":0,"squares":[{"rowIndex":0,"columnIndex":0,"visited":false}]}]}""")
    }
  }
}
