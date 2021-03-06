package com.functionalDatastructures

import com.functionalDatastructures._
sealed trait List[+A]

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def tail[A](list: List[A]): List[A] = list match {
    case Cons(h, t) => t
    case Nil => Nil
  }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}

/*object Fibonnacci {

  def fib (n :Int):Int ={
    @annotation.tailrec
    def loop(n: Int , prev :Int, cur:Int):Int = {
      if (n==0) prev
      else loop(n-1,cur,prev+cur)
    }
    loop(n,0,1)
  }

  def findElement[A](ds:Array[A], p: A => Boolean): Int ={
    @annotation.tailrec
    def loop(n:Int): Int = {
       if(n >= ds.length) -1
       else if (p(ds(n))) n
       else loop(n+1)
    }

    loop(0)
  }

}*/

object Currying {

  def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
    (a: A) => associative(a, f)
  }

  def associative[A, B, C](a: A, f: ((A, B) => C)): B => C = {
    b => f(a, b)
  }

}

object UnCurrying {

  def uncurry[A, B, C](f: A => B => C): (A, B) => C = {
    (a: A, b: B) => f(a)(b)

  }

}

object Run extends App {
  def compose[A, B, C](f: B => C, g: A => B): A => C =
    a => f(g(a))

  //implicit val iter =0;
  def f(b: Int): Int = b / 2
  def g(a: Int): Int = a + 2

  def isSorted[A](a: List[A], f: (A, A) => Boolean): Boolean = {
    // @annotation.tailrec
    def run(n: Int): Boolean = {
      //if(n >= (a.length -1)) true
      //else if (f(a(n), a(n + 1))) true
      //else run(n+1)
      true
    }
    run(0)
  }

  //if(isSorted[Int](List(2,3,5,7), (x,y) => x < y)) println("Hooooray")

  val x: List[Int] = List.tail(List(1, 2, 3, 4))

  def tail[A](lst: List[A]): List[A] =
    lst match {
      case Nil => sys.error("tail of empty list")
      case Cons(h, Nil) => Nil
      case Cons(h, t) => t
    }

  def drop[A](elem: Int, lst: List[A]): List[A] = {
    @annotation.tailrec
    def run[A](k: Int, lst: List[A]): List[A] = {
      if (elem >= k) run(k + 1, tail(lst))
      else lst
    }
    run(0, lst)
  }

  def init[A](lst: List[A]): List[A] =
    lst match {
      case Nil => Nil
      case Cons(h, Nil) => Nil
      case Cons(h, t) => Cons(h, init(t))
    }

  def foldRight[A, B](lst: List[A], z: B)(f: (A, B) => B): B =
    lst match {
      case Nil => z
      case Cons(h, t) => f(h, foldRight(t, z)(f))
    }

  //Using FoldRight which is not ideal as it throws StackOverflow for large lists
  def length[A](lst: List[A]): Int =
    lst match {
      case Nil => 0
      case Cons(h, t) => foldRight(lst, 0)((x, y) => y + 1)
    }

  def foldLeft[A, B](list: List[A], z: B)(f: (B, A) => B): B =
    list match {
      case Nil => z
      case Cons(h, t) => foldLeft(t, f(z, h))(f)
    }

  //Using FoldLeft which is ideal as it traverses the list sequentially and does not need to store function callbacks on stack
  def length2[A](lst: List[A]): Int =
    lst match {
      case Nil => 0
      case Cons(h, t) => foldLeft(lst, 0)((x, y) => x + 1)
    }

  def sumLeft(lst: List[Int]) = foldLeft(lst, 0)(_ + _)
  def sumRight(lst: List[Int]) = foldRight(lst, 0)((x, y) => x + y)

  def reverse[A](lst: List[A]) = {
    def go(l: List[A], newlist: List[A]): List[A] =
      l match {
        case Nil => Nil
        case Cons(x, Nil) => Cons(x, newlist)
        case Cons(h, t) => go(t, Cons(h, newlist))
      }
    if (lst == Nil) Nil
    else go(lst, List[A]())

  }

  def appendviaFoldRight[A](lst: List[A], new_lst: List[A]) =
    foldRight(lst, new_lst)((innerlst, new_lst) => Cons(innerlst, new_lst))

  def concat[A](lst: List[List[A]]): List[A] = foldRight(lst, Nil: List[A])((x, y) => (appendviaFoldRight(x, y)))

  def plusOne(lst: List[Int]): List[Int] = foldRight(lst, Nil: List[Int])((elem, y) => (Cons(elem + 1, y)))

  def doubleToString(lst: List[Double]): List[String] = foldRight(lst, Nil: List[String])((elem, y) => (Cons(elem.toString, y)))

  def map[A, B](l: List[A])(f: A => B): List[B] = foldRight(l, Nil: List[B])((h, t) => Cons(f(h), t))

  def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] = concat(map(l)(f))

  /* def filter[A](lst:List[A], new_lst:List[A])(f: A => Boolean):List[A] = lst match {
    case Nil => Nil
    case Cons(h,Nil) if(f(h)) =>  Nil
    case Cons(h,t) if(f(h)) => filter()
  }*/
}
