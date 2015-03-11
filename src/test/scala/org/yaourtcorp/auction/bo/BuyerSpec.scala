package org.yaourtcorp.auction
package bo

import scala.collection.immutable.SortedSet
import scala.Ordering
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BuyerSpec extends AbstractUnitSpec {

  val reservePrice = 100
  val aBuyerName = "SuperCanard"
  val someBidValuesOverReservePrice = "90 100 120"
  val someBidValuesUnderReservePrice="50 60 70"
  val buyerWithBid = BuyerConstructor(aBuyerName, reservePrice, someBidValuesOverReservePrice)
  val buyerWithNoBid = BuyerConstructor(aBuyerName, reservePrice, someBidValuesUnderReservePrice)
  
  
  "BuyerConstructor" should "create a buyer with 2 bid values" in {
		//test
		val result = BuyerConstructor(aBuyerName, reservePrice, someBidValuesOverReservePrice)
				//check
				result.isInstanceOf[Buyer] shouldBe true
				result.name shouldBe aBuyerName
				result.bidInRangeValues should contain inOrderOnly (120,100)
	}
  
	it should "create a buyer with noBid value" in {
		//test
		val result = BuyerConstructor(aBuyerName, reservePrice, someBidValuesUnderReservePrice)
        //check
        result.isInstanceOf[Buyer] shouldBe true
        result.name shouldBe aBuyerName
        result.bidInRangeValues shouldBe empty
        result.hasMadeBid() shouldBe false
	}
  
  "Buyer with bids" should "has made bids" in {
    //test
    val result = buyerWithBid.hasMadeBid()
        //check
        result shouldBe true
  }
  
   it should "have maxBid=120" in {
    //test
    val result = buyerWithBid.maxBid()
    		//check
        result.isInstanceOf[Option[Int]] shouldBe true
        result shouldBe Some(120)
  }
   
   it should "return 100 to closestBidToPrice=100" in {
	   //test
	   val result = buyerWithBid.findClosestBidToPrice(reservePrice)
			   //check
			   result.isInstanceOf[Option[Int]] shouldBe true
			   result shouldBe Some(100)
   }

   it should "None to closestBidToPrice=150" in {
	   //test
	   val result = buyerWithBid.findClosestBidToPrice(150)
			   //check
			   result.isInstanceOf[Option[Int]] shouldBe true
			   result shouldBe None
   }
   
   "Buyer with no bid" should "has not made bid" in {
    //test
    val result = buyerWithNoBid.hasMadeBid()
        //check
        result shouldBe false
  }

  it should "has maxbid = None" in {
	  //test
	  val result = buyerWithNoBid.maxBid()
        //check
        result.isInstanceOf[Option[Int]] shouldBe true
        result shouldBe None
  }
  
  it should "return None to closestBidToPrice=100" in {
     //test
     val result = buyerWithNoBid.findClosestBidToPrice(reservePrice)
         //check
         result.isInstanceOf[Option[Int]] shouldBe true
         result shouldBe None
   }
}