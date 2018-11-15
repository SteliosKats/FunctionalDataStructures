
sealed trait Option[+A] {
  def map[A, B](f: A => B): Option[B]
  def flatMap[B](f: A => Option[B]): Option[B]
  def getOrElse[B >: A](default: => B): Option[B]
  def orElse[B >: A](ob: => Option[B]): Option[B]
  def filter(f: A => Boolean): Option[A]
}

case object None extends Option[Nothing]

case class Some[A](get: A) extends Option[A]

object Option {

  def map[A, B](f: A => B): Option[B] =
    this match {
      case Some(a) => Some(f(a))
      case None => None
    }

  def getOrElse[B >: A](ob: => B): Option[B] =
    this match {
      case None => None

      case Some(a) => map(a)
    }

  def flatMap[B](f: A => Option[B]): Option[B] = this.getOrElse(a)

}

