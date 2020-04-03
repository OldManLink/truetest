package models

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json

@RunWith(classOf[JUnitRunner])
class RowSpec extends Specification {

  "Row" should {

    "correctly return requested squares that exist" in {
      Row(0, Seq(Square(4,0),Square(4,1),Square(4,2),Square(4,3),Square(4,4)))
        .getSquare(2) must beSome(Square(4, 2))
    }

    "correctly fail to return non-existent squares" in {
      Row(0, Seq(Square(4,0),Square(4,1),Square(4,2),Square(4,3),Square(4,4)))
        .getSquare(5) must beNone
    }

    "deserialise from Json" in {
      val row = Json.fromJson[Row](Json.parse("""{"index":0,"squares":[{"rowIndex":0,"columnIndex":0}]}"""))
      row.get mustEqual Row(0, Seq(Square(0,0)))
    }

    "serialise to Json" in {
      Json.toJson(Row(0, Seq(Square(0,0)))).toString() must beEqualTo("""{"index":0,"squares":[{"rowIndex":0,"columnIndex":0}]}""")
    }
  }
}
