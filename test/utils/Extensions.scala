package utils

object Extensions {

  implicit class AnyOp(val self: Any) extends AnyVal {
    def notEqualTo(other: Any): Boolean = {
      !(self equals other)
    }
  }
}
