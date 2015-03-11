package org.yaourtcorp.auction.validator

object BuyerLineValidator {

  private val buyerLineCheckRegex = "([A-Za-z])+(\\s\\d+)*"
  
   def checkBuyerLine(line: String) {
     if(!line.matches(buyerLineCheckRegex)){
          throw new IllegalArgumentException(s"Input does not respect format [buyer_name bid_value1 bid_value2 ... bid_valueN]: $line")
     }
  }
}