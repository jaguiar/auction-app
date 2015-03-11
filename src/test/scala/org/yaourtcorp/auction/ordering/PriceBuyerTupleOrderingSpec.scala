package org.yaourtcorp.auction
package ordering

import scala.collection.immutable.SortedSet

import org.junit.runner.RunWith

import bo.Buyer

import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PriceBuyerTupleOrderingSpec extends AbstractUnitSpec {

  
  
  "PriceBuyerTupleOrdering" should "order tuples" in {
		//test
    val smallTuple = (120, "petitCanard")
 		val smallTuple2 = (120, "smallCanard")
 		val bigTuple = (999, "SuperCanard")
    //check
    PriceBuyerTupleOrdering.compare(smallTuple, bigTuple) shouldBe -1
    PriceBuyerTupleOrdering.compare(bigTuple, smallTuple) shouldBe 1
    PriceBuyerTupleOrdering.compare(smallTuple, smallTuple2) should be <0 // compare names
    PriceBuyerTupleOrdering.compare(smallTuple, smallTuple) shouldBe 0
	}
  
  
}