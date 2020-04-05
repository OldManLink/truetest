package utils

object Extensions {

  implicit class Tuple2IntOps (self: (Int, Int)) {

    def <(that: (Int, Int)): (Boolean, Boolean) = ((self._1 < that._1), (self._2 < that._2))
    def >(that: (Int, Int)): (Boolean, Boolean) = ((self._1 > that._1), (self._2 > that._2))

    def +(that: (Int, Int)): (Int, Int) = (self._1 + that._1, self._2 + that._2)
    def -(that: (Int, Int)): (Int, Int) = (self._1 - that._1, self._2 - that._2)

    def /(that: Int): (Int, Int) = (quot(self._1, that), quot(self._2, that))

    def corner(that: (Int, Int)): Seq[(Int, Int)] = self._1 to that._1 flatMap(i1 => (self._2 to that._2).map(i2 => (i1, i2)))

    private def quot(x: Int, y: Int): Int = x / y
  }

  implicit class Tuple2DoubleOps (self: (Double, Double)) {

    def <(that: (Double, Double)): (Boolean, Boolean) = ((self._1 < that._1), (self._2 < that._2))
    def >(that: (Double, Double)): (Boolean, Boolean) = ((self._1 > that._1), (self._2 > that._2))

    def +(that: (Double, Double)): (Double, Double) = (self._1 + that._1, self._2 + that._2)
    def -(that: (Double, Double)): (Double, Double) = (self._1 - that._1, self._2 - that._2)

    def /(that: Double): (Double, Double) = (div(self._1, that), div(self._2, that))
    def /(that: Int): (Double, Double) = (div(self._1, that), div(self._2, that))

    private def div(x: Double, y: Double): Double = x / y
  }

  implicit class AnyOps(val self: Any) extends AnyVal {
    def notEqualTo(other: Any): Boolean = {
      !(self equals other)
    }
  }

}

/**
  *
  * Here is the Work In Progress trying to make this generic (class cast exception in the divide method)
  *
  * implicit class Tuple2Add[A: Numeric, B: Numeric](self: (A, B)) {
  *
  *   import Numeric.Implicits._
  *   import Ordering.Implicits._
  *
  *   def <(that: (A, B)): (Boolean, Boolean) = ((self._1 < that._1), (self._2 < that._2))
  *   def >(that: (A, B)): (Boolean, Boolean) = ((self._1 > that._1), (self._2 > that._2))
  *
  *   def +(that: (A, B)): (A, B) = (self._1 + that._1, self._2 + that._2)
  *   def -(that: (A, B)): (A, B) = (self._1 - that._1, self._2 - that._2)
  *
  *   def /(that: Int): (A, B) = (divide(self._1, that), divide(self._2, that))
  * }
  *
  * private def divide[A: Numeric](x: A, y: Int): A = (x.asInstanceOf[Double].intValue() / y).asInstanceOf[A]
  *
  */

