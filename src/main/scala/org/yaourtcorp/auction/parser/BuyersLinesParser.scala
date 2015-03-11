package org.yaourtcorp.auction
package parser

import scala.collection.SortedSet
import scala.collection.mutable.Builder
import bo._
import in.ConsoleReader.readBuyerLine
import in.ConsoleReader.readInt
import ordering.BuyerPriceOrdering
import ordering.PriceBuyerTupleOrdering

abstract class BuyersLinesParser[RETURN_OBJECT_TYPE, PARSED_TYPE](ordering:Ordering[RETURN_OBJECT_TYPE]){
  
  /**
   * Regexp to parse a buyerLine from StdIn
   */
  val simpleBuyerLineRegex = "([A-Za-z]+)\\s{0,1}(.*)".r
  
  /**
   * Parse input (console) and retrieve data needed to compute an auction result. 
   * $throws IllegalArgumentException if one of the line read from "std in" cannot be parsed
   */
  def parseInput() : (Int, SortedSet[RETURN_OBJECT_TYPE]) ={
    val reservePrice = readInt("minimum price of the object") // minimum price for the object
    val buyers = buildSet(reservePrice) // buyers that have made at least one bid
    (reservePrice,buyers)
  }
  
  /**
   * Build a sorted set of buyers for this auction. 
   * The only buyers that are taken into account are the one that have made bids over the reservePrice. The others are "filtered"
   * $throws IllegalArgumentException if one of the line read from "std in" cannot be parsed
   */
  private def buildSet(reservePrice: Int): SortedSet[RETURN_OBJECT_TYPE] = {
    val buyersCount = readInt("number of potential buyers") // the number of potential buyers
    val resultSet = scala.collection.mutable.SortedSet.newBuilder[RETURN_OBJECT_TYPE](ordering.reverse)
    for (i <- 0 until buyersCount) {
       val line = readBuyerLine
       val parsedObject = parseBuyerLine(line, reservePrice)
       addObjectToSet(parsedObject, resultSet)
    }
    resultSet.result()
  }
  
   private def parseBuyerLine(line: String, reservePrice: Int): PARSED_TYPE = {
      line match {
            case simpleBuyerLineRegex(name, values) => buildObject(name,values.trim, reservePrice)
            case _ => throw new IllegalArgumentException(s"Line $line cannot be parsed") // should have been checked just before
      }
   }
  
  protected def buildObject(name:String, values:String,reservePrice: Int):PARSED_TYPE
  protected def addObjectToSet(parsedObject:PARSED_TYPE, resultSet:Builder[RETURN_OBJECT_TYPE, SortedSet[RETURN_OBJECT_TYPE]])
  
}
object LineToBuyersSetParser extends BuyersLinesParser[Buyer, Buyer] (BuyerPriceOrdering){

   
  
//  /**
//   * Build a sorted set of buyers for this auction. 
//   * The only buyers that are taken into account are the one that have made bids over the reservePrice. The others are "filtered"
//   * $throws IllegalArgumentException if one of the line read from "std in" cannot be parsed
//   */
 /* private def buildBuyersSet(reservePrice: Int): SortedSet[Buyer] = {
    val buyersCount = readInt("number of potential buyers") // the number of potential buyers
    val buyers = scala.collection.mutable.SortedSet.newBuilder[Buyer](BuyerPriceOrdering.reverse)
    for (i <- 0 until buyersCount) {
       val line = readBuyerLine
       val buyer = parseBuyerLine(line, reservePrice)
        if(buyer.hasMadeBid()){//we could have used a filter function, but it's easy to check it there
          buyers+=buyer
        }
    }
    buyers.result()
  }*/
  
   override def buildObject(name:String, values:String,reservePrice: Int):Buyer={
     BuyerConstructor(name, reservePrice, values) 
   } 
   
   override def addObjectToSet(parsedObject:Buyer, resultSet:Builder[Buyer, SortedSet[Buyer]]){
      if(parsedObject.hasMadeBid()){//we could have used a filter function, but it's easy to check it there
          resultSet+=parsedObject
        }
   }
//   private def parseBuyerLine(line: String, reservePrice: Int): Buyer = {
//      line match {
//            case simpleBuyerLineRegex(name, values) => BuyerConstructor(name, reservePrice, values) 
//            case _ => throw new IllegalArgumentException(s"Line $line cannot be parsed") // should have been checked just before
//      }
//   }
}
object LineToPriceBuyerTupleSetParser extends BuyersLinesParser[(Int,String), (String, Array[Int])](PriceBuyerTupleOrdering){
  
//   /**
//   * Parse input (console) and retrieve data needed to compute an auction result. 
//   * $throws IllegalArgumentException if one of the line read from "std in" cannot be parsed
//   */
//  private def parseInput() : (Int, SortedSet[(Int, String)]) ={
//    val reservePrice = readInt("minimum price of the object") // minimum price for the object
//    val buyers = buildPricesByBuyersMap(reservePrice) // buyers that have made at least one bid
//    (reservePrice,buyers)
//  }
  
  
   override def buildObject(name:String, values:String,reservePrice: Int):(String, Array[Int])={
     (name, (reservePrice,values)) 
   } 
   
   override def addObjectToSet(parsedObject:(String, Array[Int]), resultSet:Builder[(Int, String), SortedSet[(Int, String)]]){
      for (value <- parsedObject._2) resultSet+=((value, parsedObject._1))
   }
  
   //utils: ordering and implicit conversions
  implicit def stringValues2Array(reservePriceAndValues:(Int,String)): Array[Int] = {
   reservePriceAndValues._2 match { //values
      case "" => Array.empty[Int]
      case _ => for(i <- reservePriceAndValues._2 split " " if i.toInt >= reservePriceAndValues._1) yield i.toInt
    }
  }
}