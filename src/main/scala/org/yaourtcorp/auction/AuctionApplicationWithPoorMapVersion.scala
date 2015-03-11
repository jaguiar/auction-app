package org.yaourtcorp.auction

import exception.MoreThanOneWinnerException
import exception.NoBidOverReservePriceException
import parser.LineToPriceBuyerTupleSetParser._
import scala.collection.SortedSet

/**
 * This version is a little more "brute force" but probably more optimized
 */
object AuctionApplicationWithPoorMapVersion {

  /**
   * Main Method:
   * Will read std in, expecting the following format:
   * - <reserve_price>
   * - <number_of_buyers>
   * (then foreach buyer) a line with the following format:
   * - <buyer_name> [Optional: <bid_value1> <bid_value2> ... <bid_valueN>]
   * If the buyer did not made any bidding, then the line will only contains the buyerName.
   * E.g:
   * 100 // minimum price
   * 4 // we have 4 buyers
   * Titi 120 130 150 // buyer 1 called "Titi" made 3 bids : 120 130 150
   * Toto 95 140      // buyer 2 called "Toto" made 2 bids : 95 140
   * Tutu             // buyer 3 called "Tutu" made no bid
   * Tata 125         // buyer 2 called "Tata" made 1 bid : 125 
   * ----- 
   * The buyerName can only contains "letters" (a-zA-Z)
   * The bid values can only be digits (and is an Int, Long not supported at the moment ;))
   * -------
   * Output:
   * - if there no buyer made a bid or the reserve price has not been reached or the reserve price has not been reached => application will throw a $NoBidOverReservePriceException
   * - if there is more than one buyer with the max bid => application will throw a $MoreThanOneWinnerException
   * - else the application will answer (on Std out) "WINNER : <Winner_Name> - BID PRICE = <Bid_Price>"
   * $throw MoreThanOneWinnerException
   * $throw NoBidOverReservePriceException
   */
	def main(args: Array[String]): Unit = {
			val (reservePrice, pricesByBuyers) = parseInput()
      val (winnerName, bestPrice) = computeWinnerNameAndBestPrice(reservePrice, pricesByBuyers)
        
      println(s"WINNER : $winnerName - BID PRICE = $bestPrice")
	}
  
  /**
   * Compute the winner name and the best price. 
   * Reserve price is only needed for the exception message, so could be discarded
   */
  private def computeWinnerNameAndBestPrice(reservePrice:Int, pricesByBuyers:SortedSet[(Int, String)]):(String, Int) = {
    pricesByBuyers.headOption match {
          case None => throw new NoBidOverReservePriceException(reservePrice) // fist case: no buyer over reserve price
          case Some((winnerMaxPrice, winnerName)) => {
            //we look for the second best buyer if he exists, aka the first price with a buyerName not being the winner
            pricesByBuyers.find(priceAndBuyer => !winnerName.equals(priceAndBuyer._2)) match {
              case None => (winnerName, pricesByBuyers.lastKey._1)//only one buyer, we just have to take the smallest price
              case Some((priceOfSecondBestBuyer, _)) => { //look for the price of the second best buyer
                if(priceOfSecondBestBuyer == winnerMaxPrice) throw new MoreThanOneWinnerException // we have several winners
                (winnerName, priceOfSecondBestBuyer) 
              }  
          } 
        }
      } 
  }
//  /**
//   * Parse input (console) and retrieve data needed to compute an auction result. 
//   * $throws IllegalArgumentException if one of the line read from "std in" cannot be parsed
//   */
//  private def parseInput() : (Int, SortedSet[(Int, String)]) ={
//    val reservePrice = readInt("minimum price of the object") // minimum price for the object
//    val buyers = buildPricesByBuyersMap(reservePrice) // buyers that have made at least one bid
//    (reservePrice,buyers)
//  }
//  
//  /**
//   * Build a sorted set of buyers for this auction. 
//   * The only buyer that are taken into account are the one that have made bids. The others are "filtered"
//   * $throws IllegalArgumentException if one of the line read from "std in" cannot be parsed
//   */
//  private def buildPricesByBuyersMap(reservePrice: Int): SortedSet[(Int, String)] = {
//    val buyersCount = readInt("number of potential buyers") // the number of potential buyers
//    // would have been better to use TreeMap, but there is no mutable version of it, so it's a "treemap du pauvre" I guess
//    val pricesByBuyers = SortedSet.newBuilder[(Int,String)](PriceBuyerTupleOrdering.reverse)
//    for (i <- 0 until buyersCount) {
//       val line = readBuyerLine
//       val (name, values) = parseBuyerLine(line, reservePrice)
//       for (value <- values) pricesByBuyers+=((value, name))
//    }
//    pricesByBuyers.result()
//  }
//  
//   private def parseBuyerLine(line: String, reservePrice: Int): (String, Array[Int]) = {
//      line match {
//            case simpleBuyerLineRegex(name, values) => (name, (reservePrice,values)) 
//            case _ => throw new IllegalArgumentException(s"Line $line cannot be parsed") // should have been checked just before
//      }
//   }
//   
//   //utils: ordering and implicit conversions
//  object PriceBuyerTupleOrdering extends Ordering[(Int, String)] {
//  //we could have done directly reverse ordering by reverting tuple1 and tuple2, but it could lead to misunderstanding, and Ordering has a "native reverse method" anyway
//   def compare(tuple1:(Int, String), tuple2:(Int, String)) = tuple1._1 compare tuple2._1
//  }
//  
//  implicit def stringValues2Array(reservePriceAndValues:(Int,String)): Array[Int] = {
//    for(i <- reservePriceAndValues._2 split " " if i.toInt >= reservePriceAndValues._1) yield i.toInt
//  }
}