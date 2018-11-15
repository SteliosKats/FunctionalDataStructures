import Stream._

sealed trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h)
  }

  def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
    case Cons(h, t) => f(h(), t.foldRight(z)(f))

    case _ => z
  }

  //Convert a Strem to List , the functional waay
  def toList: List[A] = this match {
    case Cons(h, t) => h() :: t().toList()
    case Empty => Nil
  }

  def forAll(p: A => Boolean): Boolean = this match {
    case Cons(h, t) if (p(h)) => t().forAll(p)

    case Cons(h, t) if (!p(h)) => false

    case _ => true
  }

  def forAllviaFold(p: A => Boolean): Boolean = this.foldRight(true)((h, r) => p(h) && r)

}

