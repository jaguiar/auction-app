package org.yaourtcorp.auction

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.scalatest.BeforeAndAfter
import java.io.ByteArrayInputStream
import exception.NoBidOverReservePriceException
import exception.MoreThanOneWinnerException

@RunWith(classOf[JUnitRunner])
class AuctionApplicationWithObjectVersionSpec extends AbstractUnitSpec with BeforeAndAfter {

  // limit cases, don't do that at home!
  val zeroBuyerCase = """|120
                         |0""".stripMargin
                         
  val noBuyerOverReservePriceCase = """|120
                                       |3
                                       |petitCanard 110 100
                                       |smallCanard
                                       |littleDuck 119""".stripMargin
                         
  val twoWinnersCase = """|120
                          |3
                          |petitCanard 120 100
                          |smallCanard
                          |littleDuck 120""".stripMargin
                         
  //"normal cases":these cases are fakes, I mean, we know that SuperCanard always wins
  val oneBuyerCase = """|120
                         |1
                         |SuperCanard 150 100 145""".stripMargin
                         
  val twoBuyersCase = """|120
                         |2
                         |petitCanard 100 90 120
                         |SuperCanard 145 150 100""".stripMargin
  
  val severalBuyersCase = """|100
                         |5
                         |petitCanard 110 130
                         |smallCanard
                         |littleDuck 125
                         |petitDuck 105 115 90
                         |SuperCanard 132 135 140""".stripMargin
                         
  // streams used for testing program output.
  val out = new ByteArrayOutputStream
  val printStream = new PrintStream(out)
  
  after {
    out.reset
  }
                         
  "AuctionApplicationWithObjectVersion" should "throw a NoBidOverReservePriceException when there is no buyer" in {
    //test
    val inputStream = new ByteArrayInputStream(zeroBuyerCase.getBytes());
    intercept[NoBidOverReservePriceException] { 
      Console.withOut(printStream)({
       Console.withIn(inputStream)(AuctionApplicationWithObjectVersion.main(Array.empty))
     })
    }
  }
  
  it should "throw a noBuyerOverReservePriceCase when there is no bid over reserve price" in {
    //test
    val inputStream = new ByteArrayInputStream(noBuyerOverReservePriceCase.getBytes());
    intercept[NoBidOverReservePriceException] { 
      Console.withOut(printStream)({
       Console.withIn(inputStream)(AuctionApplicationWithObjectVersion.main(Array.empty))
     })
    }
  }
  
  it should "throw a MoreThanOneWinnerException" in {
    //test
    val inputStream = new ByteArrayInputStream(twoWinnersCase.getBytes());
    intercept[MoreThanOneWinnerException] { 
      Console.withOut(printStream)({
       Console.withIn(inputStream)(AuctionApplicationWithObjectVersion.main(Array.empty))
     })
    }
  }

  it should "find that SuperCanard always wins when he is alone" in {
	  //test
	  val inputStream = new ByteArrayInputStream(oneBuyerCase.getBytes());
    //test
		Console.withOut(printStream)({
			  Console.withIn(inputStream)(AuctionApplicationWithObjectVersion.main(Array.empty))
		})
    //check
    out.toString().trim shouldBe """Please enter the minimum price of the object
      |Please enter the number of potential buyers
      |Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]
      |WINNER : SuperCanard - BID PRICE = 145""".stripMargin
  }
  
  it should "find that SuperCanard always wins even if there are 2 buyers" in {
	  //test
	  val inputStream = new ByteArrayInputStream(twoBuyersCase.getBytes());
	  //test
	  Console.withOut(printStream)({
		  Console.withIn(inputStream)(AuctionApplicationWithObjectVersion.main(Array.empty))
	  })
	  //check
	  out.toString().trim shouldBe """Please enter the minimum price of the object
	  |Please enter the number of potential buyers
	  |Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]
	  |Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]
	  |WINNER : SuperCanard - BID PRICE = 120""".stripMargin
  }
  
  it should "find that SuperCanard always wins whatever the number of buyers" in {
	  //test
	  val inputStream = new ByteArrayInputStream(severalBuyersCase.getBytes());
	  //test
	  Console.withOut(printStream)({
		  Console.withIn(inputStream)(AuctionApplicationWithObjectVersion.main(Array.empty))
	  })
	  //check
	  out.toString().trim shouldBe """Please enter the minimum price of the object
	  |Please enter the number of potential buyers
	  |Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]
	  |Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]
	  |Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]
	  |Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]
	  |Please enter next Buyer bids, input format is [Buyer_name] [Bid_price1] [Bid_price2]... [Bid_priceN]
	  |WINNER : SuperCanard - BID PRICE = 130""".stripMargin
  }

  
 
  
}