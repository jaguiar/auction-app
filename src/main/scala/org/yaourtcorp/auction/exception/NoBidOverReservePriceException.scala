package org.yaourtcorp.auction.exception

class NoBidOverReservePriceException(reservePrice: Int) extends Exception(s"No bid over $reservePrice") {

}