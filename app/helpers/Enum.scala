package helpers

trait Enum[A]
{
  trait Value
  {
    self: A => //noinspection ForwardReference
    _values :+= this
  }
  private var _values = List.empty[A]
  def values: Seq[A] = _values
}