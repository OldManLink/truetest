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

    "correctly return requested squares that exist" in {
      Board(BoardDescription(10, 10)).getSquare(5, 5) must beSome(Square(5, 5))
    }

    "correctly fail to return non-existent squares" in {
      Board(BoardDescription(5, 5)).getSquare(5, 5) must beNone
    }

    "deserialise from Json" in {
      val board = Json.fromJson[Board](Json.parse("""{"rows":[{"index":0,"squares":[{"rowIndex":0,"columnIndex":0}]}]}"""))
      board.get mustEqual(Board(BoardDescription(1, 1)))
    }

    "serialise to Json" in {
      Json.toJson(Board(BoardDescription(1, 1))).toString() must beEqualTo("""{"rows":[{"index":0,"squares":[{"rowIndex":0,"columnIndex":0}]}]}""")
    }
  }
}
