package org.yaourtcorp.auction
package in

import scala.collection.immutable.SortedSet
import ConsoleReader._
import scala.Ordering
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import java.io.PrintStream
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.ByteArrayOutputStream

@RunWith(classOf[JUnitRunner])
class ConsoleReaderSpec extends AbstractUnitSpec with BeforeAndAfter {

  // streams used for testing program output.
  val out = new ByteArrayOutputStream
  val printStream = new PrintStream(out)
  
  after {
    out.reset
  }
  
  "ConsoleReader" should "readInt" in {
		//test
    val inputStream = new ByteArrayInputStream("12".getBytes());
    val result = Console.withOut(printStream)({
		  Console.withIn(inputStream)(readInt("number of cats"))
    })
				//check
				result.isInstanceOf[Int] shouldBe true
				result shouldBe 12
        out.toString shouldBe "Please enter the number of cats\n"
	}
  
  it should "write an error message and readInt" in {
    //test
    val inputStream = new ByteArrayInputStream("toto\n12".getBytes());
    val result = Console.withOut(printStream)({
      Console.withIn(inputStream)(readInt("number of cats"))
    })
        //check
        result.isInstanceOf[Int] shouldBe true
        result shouldBe 12
        println(out.toString)
        out.toString shouldBe "Please enter the number of cats\nSorry, I didn't understand, there was an error in your input I guess.\nPlease enter the number of cats\n"
  }
  it should "read a Buyer line with bids" in {
	  //test
	  val inputStream = new ByteArrayInputStream("SuperCanard 120 123".getBytes());
	  val result = Console.withOut(printStream)({
		  Console.withIn(inputStream)(readBuyerLine)
	  })
	  //check
	  result.isInstanceOf[String] shouldBe true
	  result shouldBe "SuperCanard 120 123"
	  println(out.toString)
	  out.toString shouldBe "Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]\n"
  }
  
  it should "read a Buyer line without bid" in {
	  //test
	  val inputStream = new ByteArrayInputStream("PetitCanard".getBytes());
	  val result = Console.withOut(printStream)({
		  Console.withIn(inputStream)(readBuyerLine)
	  })
	  //check
	  result.isInstanceOf[String] shouldBe true
	  result shouldBe "PetitCanard"
	  println(out.toString)
	  out.toString shouldBe "Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]\n"
  }
  
  it should "throw an exception if the line format is invalid" in {
    intercept[IllegalArgumentException] {
      //test
      val inputStream = new ByteArrayInputStream("PetitCanard est un petit canard".getBytes());
      Console.withIn(inputStream)(readBuyerLine)
    }
  }
  
}