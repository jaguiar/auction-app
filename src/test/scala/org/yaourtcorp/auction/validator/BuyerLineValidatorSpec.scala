package org.yaourtcorp.auction
package validator

import org.junit.runner.RunWith

import BuyerLineValidator.checkBuyerLine

import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BuyerLineValidatorSpec extends AbstractUnitSpec {

  
  
  "BuyerLineValidator" should "validate correct line" in {
		//test
    checkBuyerLine("SuperCanard 120 123 125")
	}
  
  it should "throw an exception if the line format is invalid" in {
    intercept[IllegalArgumentException] {
      //test
      checkBuyerLine("PetitCanard est un petit canard");
    }
  }
  
}