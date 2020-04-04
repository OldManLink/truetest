package helpers

import play.api.libs.json._
import play.api.libs.json.JsError.{apply => JsError}

sealed abstract class Move(val code: String, val y: Int, val x: Int) extends Move.Value {

  @Override
  override def toString: String = code
}

object Move extends Enum[Move] {

  def apply(code: String): Move = values.find(m => m.code == code).getOrElse(O)
  def unapply(move: Move): Option[String] = Some(move.code)
  def validMoves: Seq[Move] = values.filterNot(_.equals(O))

  implicit object MoveFormat extends Format[Move] {
    def reads(json: JsValue): JsResult[Move] = json match {
      case JsString(code) => JsSuccess[Move](Move(code))
      case _ => JsError(JsonValidationError("Invalid JSON input found"))
    }
    def writes(move: Move): JsValue = JsString(move.code)
  }

  val O:  Move = new Move("O",  0,  0){}
  val N:  Move = new Move("N",  3,  0){}
  val NE: Move = new Move("NE", 2,  2){}
  val E:  Move = new Move("E",  0,  3){}
  val SE: Move = new Move("SE",-2,  2){}
  val S:  Move = new Move("S", -3,  0){}
  val SW: Move = new Move("SW",-2, -2){}
  val W:  Move = new Move("W",  0, -3){}
  val NW: Move = new Move("NW", 2, -2){}
}                                  