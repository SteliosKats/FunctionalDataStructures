package com.functionalDatastructures

sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]

case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {

  def size[A](tree: Tree[A]): Int = tree match {

    case Leaf(value) => 1

    case Branch(left, right) => 1 + size(left) + size(right)
  }

  def maximum(tree: Tree[Int]): Int = tree match {

    case Leaf(value) => value

    case Branch(left, right) => (maximum(left)).max(maximum(right))

  }

  def depth(tree: Tree[Int]): Int = tree match {

    case Leaf(value) => 0

    case Branch(left, right) => 1 + (depth(left) max depth(right))

  }

  def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match {

    case Leaf(value) => Leaf(f(value))

    case Branch(left, right) => Branch(map(left)(f), map(right)(f))

  }

  def fold[A, B](t: Tree[A])(f: A => B)(g: (B, B) => B): B = t match {
    case Leaf(a) => f(a)

    case Branch(l, r) => g(fold(l)(f)(g), fold(r)(f)(g))
  }

  def sizeViaFold[A](t: Tree[A]): Int = fold(t)(a => 1)(1 + _ + _)

}

object Run2 extends App {
  def t = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))
  println("OJK " + Tree.sizeViaFold(t))
}