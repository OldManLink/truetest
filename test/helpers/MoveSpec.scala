package helpers

import helpers.Move.NW
import models.{Board, BoardDescription, Square}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json

@RunWith(classOf[JUnitRunner])
class MoveSpec extends Specification {

  "Move" should {

    "have the correct destination" in {
      val board = Board(BoardDescription(10, 10))
      NW.destination(board, Square(1, 2)) mustEqual Step(Some(Square(3, 0)), NW)
      NW.destination(board, Square(9, 0)) mustEqual Step(None, NW)
    }

    "deserialise from Json" in {
      val move = Json.fromJson[Move](Json.parse("\"NW\""))
      move.get mustEqual NW
    }

    "serialise to Json" in {
      Json.toJson(NW).toString() must beEqualTo("\"NW\"")
    }
  }
}
