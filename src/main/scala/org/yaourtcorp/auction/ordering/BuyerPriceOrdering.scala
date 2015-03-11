package org.yaourtcorp.auction
package ordering

import bo.Buyer

object BuyerPriceOrdering extends Ordering[Buyer] {
  //we consider that only Buyers with bids over reservePrice will be compared, else with should have put a default value to "0" so that there are not taken into account 
  //we could have done directly reverse ordering by reverting buyer1 and buyer2, but it could lead to misunderstanding, and Ordering has a "native reverse method" anyway
   def compare(buyer1:Buyer, buyer2:Buyer) = {
    val compareValue = buyer1.maxBid.get compare buyer2.maxBid.get
    compareValue match {
      case 0 => buyer1.name compare buyer2.name
      case _ => compareValue
    }
   }  
}