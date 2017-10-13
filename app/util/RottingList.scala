package util

import scala.collection.mutable

abstract class RottingList[T] {

  def isRotten(t: T): Boolean

  private val queue = mutable.Queue[T]()

  def find(cond: T => Boolean): Option[T]={
    prune()
    queue.find(cond)
  }
  
  def toList = {
    prune()
    queue.toList
  }


  def ++=(elems: Seq[T]): Unit = {
    prune()
    queue ++= elems
    ()
  }

  private def prune(): Unit = {
    while (queue.headOption match {
      case None => false
      case Some(t) =>
        isRotten(t)
    }) queue.dequeue()
  }
}

object RottingList {
  def apply[T](isRotten: T => Boolean): RottingList[T] = (t: T) => isRotten(t)
}


