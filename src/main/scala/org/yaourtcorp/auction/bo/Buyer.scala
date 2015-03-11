package org.yaourtcorp.auction.bo

import scala.collection.immutable.SortedSet
import scala.Ordering

case class Buyer(name:String, bidInRangeValues: SortedSet[Int]){
  
  /**
   * check if a buyer has made bid
   */
  def hasMadeBid(): Boolean = {
    !bidInRangeValues.isEmpty
  }
  
  def findClosestBidToPrice(price: Int): Option[Int] ={
      bidInRangeValues.to(price).lastOption
  }

  // look for the buyer with the biggest bid (since we use a reverse sortedset, we can be sure that the first bid value is the biggest)
  def maxBid(): Option[Int] ={
		  bidInRangeValues.headOption
  }
  
  override def hashCode = 41 * name.hashCode
  override def equals(other: Any) = other match { 
      case that: Buyer => this.name == that.name 
      case _ => false 
    }
}

object BuyerConstructor {
  
  // implicit conversion function from values as a String to a sortedSet with only values > reservePrice
  implicit def stringValues2SortedSet(reservePriceAndValues:(Int,String)): SortedSet[Int] = {
    val array = reservePriceAndValues._2 match { //values
      case "" => Array.empty[Int]
      case _ => for(i <- reservePriceAndValues._2 split " " if i.toInt >= reservePriceAndValues._1) yield i.toInt
    }
    SortedSet(array : _*)(Ordering[Int].reverse)
  }

  def apply(name:String, reservePrice: Int, values: String) = {
     new Buyer(name, (reservePrice,values))
  }
}