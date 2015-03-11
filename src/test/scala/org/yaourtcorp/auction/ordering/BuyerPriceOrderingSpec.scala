package org.yaourtcorp.auction
package ordering

import scala.collection.immutable.SortedSet

import org.junit.runner.RunWith

import bo.Buyer

import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BuyerLineValidatorSpec extends AbstractUnitSpec {

  
  
  "BuyerPriceOrdering" should "order buyers" in {
		//test
    val smallBuyer = new Buyer("petitCanard", SortedSet(120,110,100))
    val smallBuyer2 = new Buyer("smallCanard", SortedSet(120,100))
    val bigBuyer = new Buyer("SuperCanard", SortedSet(1500,1400,1600))
    //check
    BuyerPriceOrdering.compare(smallBuyer, bigBuyer) shouldBe -1
    BuyerPriceOrdering.compare(bigBuyer, smallBuyer) shouldBe 1
    BuyerPriceOrdering.compare(smallBuyer, smallBuyer) shouldBe 0
    BuyerPriceOrdering.compare(smallBuyer, smallBuyer2) should be < 0 // compare names
	}
  
  
}