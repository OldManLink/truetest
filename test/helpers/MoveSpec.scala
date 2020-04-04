package helpers

import helpers.Move.{NW, O}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{JsError, JsResult, JsValue, Json}

@RunWith(classOf[JUnitRunner])
class MoveSpec extends Specification {

  "Move" should {

    "unapply correctly" in {
      NW match {
        case Move("NW") => success
        case _ => failure("Wrong match")
      }
    }

    "serialise to Json" in {
      Json.toJson(NW).toString() must beEqualTo("\"NW\"")
    }

    "deserialise from Json" in {
      Json.fromJson[Move](Json.parse("\"NW\"")).get mustEqual NW
    }

    "defult deserialise from Json for unknown code" in {
      Json.fromJson[Move](Json.parse("\"FOO\"")).get mustEqual O
    }

    "fail to deserialise from bad Json" in {
      Json.fromJson[Move](Json.parse("42")) must beLike[JsResult[Move]] {
        case JsError(details) =>
          details.headOption.flatMap(_._2.headOption)
            .map(e => e.message) must beSome( "Invalid JSON input found")
        case _ => ko
      }
    }
  }
}
