package org.yaourtcorp.auction

import scala.collection.SortedSet
import scala.collection
import scala.Ordering
import exception.NoBidOverReservePriceException
import exception.MoreThanOneWinnerException
import bo._
import in.ConsoleReader.{readInt, readBuyerLine}
import parser.LineToBuyersSetParser.parseInput
object AuctionApplicationWithObjectVersion {

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
			val (reservePrice, buyers) = parseInput()
      
      // check if we have buyers over reserve price
      val (winnerName, bestPrice) = computeWinnerNameAndBestPrice(reservePrice, buyers)
      println(s"WINNER : $winnerName - BID PRICE = $bestPrice")
	}
  
  /**
   * Compute the winner name and the best price. 
   * Reserve price is only needed for the exception message, so could be discarded
   */
  private def computeWinnerNameAndBestPrice(reservePrice:Int, buyers:SortedSet[Buyer]):(String, Int) = {
    buyers.size match { // we can pattern match only on a list/seq but it will be to slow to copy all the set
        case 0 => throw new NoBidOverReservePriceException(reservePrice)
        case 1 => (buyers.head.name, buyers.head.findClosestBidToPrice(reservePrice).get) //there is only one, so everything is cool
        case _ => {
          val first = buyers.head
          val second = buyers.tail.head
          //let's compare the first with the second one 
          if(first.maxBid().get == second.maxBid().get){//guess there is no winner
            throw new MoreThanOneWinnerException
          }
         (first.name, second.maxBid().get) 
        }
      }
  }
//  /**
//   * Parse input (console) and retrieve data needed to compute an auction result. 
//   * $throws IllegalArgumentException if one of the line read from "std in" cannot be parsed
//   */
//  private def parseInput() : (Int, SortedSet[Buyer]) ={
//    val reservePrice = readInt("minimum price of the object") // minimum price for the object
//    val buyers = buildBuyerSet(reservePrice) // buyers that have made at least one bid
//    (reservePrice,buyers)
//  }
//  
//  /**
//   * Build a sorted set of buyers for this auction. 
//   * The only buyers that are taken into account are the one that have made bids over the reservePrice. The others are "filtered"
//   * $throws IllegalArgumentException if one of the line read from "std in" cannot be parsed
//   */
//  private def buildBuyerSet(reservePrice: Int): SortedSet[Buyer] = {
//    val buyersCount = readInt("number of potential buyers") // the number of potential buyers
//    val buyers = scala.collection.mutable.SortedSet.newBuilder[Buyer](BuyerPriceOrdering.reverse)
//    for (i <- 0 until buyersCount) {
//       val line = readBuyerLine
//       val buyer = parseBuyerLine(line, reservePrice)
//        if(buyer.hasMadeBid()){//we could have used a filter function, but it's easy to check it there
//          buyers+=buyer
//        }
//    }
//    buyers.result()
//  }
//  
//   private def parseBuyerLine(line: String, reservePrice: Int): Buyer = {
//      line match {
//            case simpleBuyerLineRegex(name, values) => BuyerConstructor(name, reservePrice, values) 
//            case _ => throw new IllegalArgumentException(s"Line $line cannot be parsed") // should have been checked just before
//      }
//   }
}