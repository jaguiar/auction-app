package org.yaourtcorp.auction.exception

// should have been called TooManyWinnersException
class MoreThanOneWinnerException extends Exception("We found more than one winner for the max bid!") {

}