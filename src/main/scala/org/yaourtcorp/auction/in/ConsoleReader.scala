package org.yaourtcorp.auction
package in

import scala.io.StdIn
import validator.BuyerLineValidator.checkBuyerLine

object ConsoleReader {

  val errorMessage = "Sorry, I didn't understand, there was an error in your input I guess.\n"
  val askForSomeIntMessage = "%sPlease enter the %s\n"
  val askForBuyerBidsMessage = "Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]\n"
  
  //special mix of readInt/readline
  def readInt(argName:String):Int = {
    var s = StdIn.readLine(askForSomeIntMessage, "", argName);
    while (s == null || !s.matches("\\d+")) {
      s = StdIn.readLine(askForSomeIntMessage, errorMessage, argName)
    } 
    s.toInt
  }
  def readBuyerLine(): String = {
    val s = StdIn.readLine(askForBuyerBidsMessage);
    checkBuyerLine(s)
    s
  }
}