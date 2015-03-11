package org.yaourtcorp.auction
package parser

import scala.Ordering
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import java.io.ByteArrayInputStream
import scala.collection._
import bo.Buyer

@RunWith(classOf[JUnitRunner])
class BuyersLinesParserSpec extends AbstractUnitSpec {

  val zeroBuyerCase = """|120
                         |0""".stripMargin
  val oneBuyerCase = """|120
                         |1
                         |SuperCanard 150 100 145""".stripMargin
                         
  val twoBuyersCase = """|120
                         |2
                         |petitCanard 100 90 120
                         |SuperCanard 145 150 100""".stripMargin
  
                      
  "LineToBuyersSetParser" should "parse zero buyer" in {
    //test
    val inputStream = new ByteArrayInputStream(zeroBuyerCase.getBytes());
    val (reservePrice, setOfBuyers) = Console.withIn(inputStream)(LineToBuyersSetParser.parseInput())
        //check
        reservePrice.isInstanceOf[Int] shouldBe true
        reservePrice shouldBe 120 
        setOfBuyers.isInstanceOf[SortedSet[Buyer]] shouldBe true
        setOfBuyers shouldBe empty
  }
  
  it should "parse one buyer" in {
    //test
    val inputStream = new ByteArrayInputStream(oneBuyerCase.getBytes());
    val (reservePrice, setOfBuyers) = Console.withIn(inputStream)(LineToBuyersSetParser.parseInput())
        //check
        reservePrice.isInstanceOf[Int] shouldBe true
        reservePrice shouldBe 120 
        setOfBuyers.isInstanceOf[SortedSet[Buyer]] shouldBe true
        setOfBuyers should not be empty
        setOfBuyers.head shouldBe Buyer("SuperCanard", immutable.SortedSet(150,145))
  }

  it should "parse two buyers" in {
	  //test
	  val inputStream = new ByteArrayInputStream(twoBuyersCase.getBytes());
	  val (reservePrice, setOfBuyers) = Console.withIn(inputStream)(LineToBuyersSetParser.parseInput())
			  //check
			  reservePrice.isInstanceOf[Int] shouldBe true
			  reservePrice shouldBe 120 
			  setOfBuyers.isInstanceOf[SortedSet[Buyer]] shouldBe true
			  setOfBuyers should not be empty
			  setOfBuyers should contain inOrderOnly(Buyer("SuperCanard", immutable.SortedSet(150,145)),Buyer("petitCanard", immutable.SortedSet(120)))
  }
  
  
  "LineToPriceBuyerTupleSetParser" should "parse zero buyer" in {
    //test
    val inputStream = new ByteArrayInputStream(zeroBuyerCase.getBytes());
    val (reservePrice, setOfPriceBuyerTuples) = Console.withIn(inputStream)(LineToPriceBuyerTupleSetParser.parseInput())
        //check
        reservePrice.isInstanceOf[Int] shouldBe true
        reservePrice shouldBe 120 
        setOfPriceBuyerTuples.isInstanceOf[SortedSet[(Int,String)]] shouldBe true
        setOfPriceBuyerTuples shouldBe empty
  }
  
  it should "parse one buyer" in {
    //test
    val inputStream = new ByteArrayInputStream(oneBuyerCase.getBytes());
    val (reservePrice, setOfPriceBuyerTuples) = Console.withIn(inputStream)(LineToPriceBuyerTupleSetParser.parseInput())
        //check
        reservePrice.isInstanceOf[Int] shouldBe true
        reservePrice shouldBe 120 
        setOfPriceBuyerTuples.isInstanceOf[SortedSet[(Int,String)]] shouldBe true
        setOfPriceBuyerTuples should not be empty
        setOfPriceBuyerTuples should contain inOrderOnly((150,"SuperCanard"), (145,"SuperCanard"))
  }

  it should "parse two buyers" in {
    //test
    val inputStream = new ByteArrayInputStream(twoBuyersCase.getBytes());
    val (reservePrice, setOfPriceBuyerTuples) = Console.withIn(inputStream)(LineToPriceBuyerTupleSetParser.parseInput())
        //check
        reservePrice.isInstanceOf[Int] shouldBe true
        reservePrice shouldBe 120 
        setOfPriceBuyerTuples.isInstanceOf[SortedSet[(Int,String)]] shouldBe true
        setOfPriceBuyerTuples should not be empty
        setOfPriceBuyerTuples should contain inOrderOnly((150,"SuperCanard"), (145,"SuperCanard"),(120,"petitCanard"))
  }
  
}