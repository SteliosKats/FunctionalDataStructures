/*package com.functionalDatastructures

import scala.collection.immutable.Stream._

sealed trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Empty      => None
    case Cons(h, t) => Some(h)
  }

  def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
    case Cons(h, t) => f(h(), t.foldRight(z)(f))

    case _          => z
  }

  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) if n > 1  => cons(h(), t().take(n - 1))
    case Cons(h, _) if n == 1 => cons(h(), empty)
    case _                    => Empty
  }

  //Convert a Stream to List , the functional way
  def toList: List[A] = this match {
    case Cons(h, t) => h() :: t().toList()
    case Empty      => Nil
  }

  def forAll(p: A => Boolean): Boolean = this match {
    case Cons(h, t) if (p(h))  => t().forAll(p)

    case Cons(h, t) if (!p(h)) => false

    case _                     => true
  }

  def forAllviaFold(p: A => Boolean): Boolean = this.foldRight(true)((h, r) => p(h) && r)

  def constant[A](a: A): Stream[A] = cons(a, constant(a))
}

case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = Stream.cons(1, ones)

  // Exercise 5.8
  // Generalize ones slightly to the function constant, which returns an infinite
  // Stream of a given value.
  def constant[A](a: A): Stream[A] = cons(a, constant(a))

  // Exercise 5.9
  // Write a function that generates an infinite stream of integers, starting
  // from n, then n + 1, n + 2, and so on.
  def from(n: Int): Stream[Int] = cons(n, from(n + 1))

  // Exercise 5.10
  // Write a function fibs that generates the infinite stream of Fibonacci numbers:
  // 0, 1, 1, 2, 3, 5, 8, and so on.
  def fibs: Stream[Int] = {
    def loop(previous: Int, current: Int): Stream[Int] =
      cons(previous, loop(current, previous + current))
    loop(0, 1)
  }

  // Exercise 5.11
  // Write a more general stream-building function called unfold. It takes an
  // initial state, and a function for producing both the next state and the
  // next value in the generated stream.
  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = f(z) match {
    case None         => empty
    case Some((a, s)) => cons(a, unfold(s)(f))
  }

  // Exercise 5.12
  // Write fibs, from, constant, and ones in terms of unfold.
  def fibsViaUnfold: Stream[Int] = unfold((0, 1)) {
    case (i, j) => Some((i, (j, i + j)))
  }
  def fromViaUnfold(n: Int): Stream[Int] = unfold(n)(i => Some((i, i + 1)))
  def constantViaUnfold[A](a: A): Stream[A] = unfold(a)(_ => Some((a, a)))
  val onesViaUnfold: Stream[Int] = unfold(1)(_ => Some((1, 1)))
}
*/