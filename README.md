Auction application for Yaourtcorp 
============

Auction application POC

This application needs :

* Scala 11.4 
        [http://scala-lang.org/download/2.11.4.html]

* Java JDK 7 or 8 
        [http://www.oracle.com/technetwork/java/javase/downloads/index.html]
        
* Sbt 13+ (Optional)
        [http://www.scala-sbt.org/download.html]

## Run the application

* Unzip the source
* Open terminal
* Go to the project root
* Execute

```
#!shell

sbt clean compile test
sbt run
```
* select one of the two available algorithms (see below)
* follow the instructions on the input. The expected format is the following:

```
- <reserve_price>
   * - <number_of_buyers>
   * (then foreach buyer) a line with the following format:
   * - <buyer_name> [Optional: <bid_value1> <bid_value2> ... <bid_valueN>]
   * If the buyer did not made any bidding, then the line will only contains the buyerName.
   * E.g:
   * 100 // minimum price
   * 4 // we have 4 buyers
   * Titi 120 130 150 // buyer 1 called "Titi" made 3 bids : 120 130 150
   * Toto 95 140      // buyer 2 called "Toto" made 2 bids : 95 140
   * Tutu             // buyer 3 called "Tutu" made no bid
   * Tata 125         // buyer 2 called "Tata" made 1 bid : 125 
   * ----- 
   * The buyerName can only contains "letters" (a-zA-Z)
   * The bid values can only be digits (and is an Int, Long not supported at the moment ;))

```
## There are two versions available for the algorithm
    * The first one is based on objects and is accessible with org.yaourtcorp.auction.AuctionApplicationWithObjectVersion#main method
    * The second one is based on tuples and set and is accessible with org.yaourtcorp.auction.AuctionApplicationWithPoorMapVersion#main method

## Notes
This application assumes that there was no error in the given example, so that:
* The winner is the buyer who has made the max bid of all
* The price is the max bid price of the second best buyer (and not the bid price of the winner, that is closest to the max price made by the second best buyer)
* The latter seems a little "counter-intuitive" for me because I would have supposed you would pay the price you said and not the price another offered.

If in fact, the example contains a mistake, and the price shall be the winner's one that is closest (>) to the one offered by the second best buyer , then:
* the AuctionApplicationWithObjectVersion shall not be too complicated to change to answer this case (1 line)
* the AuctionApplicationWithPoorMapVersion is a little more tough

## Original wording (in French)
### Problem
Prenons en considération un système d’enchères en un tour au second prix.
* Un objet mis en vente avec un prix de réserve.
* Nous avons plusieurs acheteurs potentiels. Chacun d'entre eux peut placer une ou plusieurs enchère(s).
* L'acheteur remportant l'enchère est celui ayant le plus enchéri au dessus du prix de réserve.
* Le montant de l’enchère gagnante est le plus petit prix supérieur au prix de réserve permettant de remporter l’enchère sans s'auto concurrencer.
     
### Exemple
* Considérons 5 acheteurs potentiels (A, B, C, D, E) compétitant pour l'aquisition d'un objet dont le prix de réserve a été établi à 100 euros.
     
* Les acheteurs enchérissent comme suit :
** A : 2 enchères de 110 et 130 euros
** B : 0 enchère
** C : 1 enchère de 125 euros
** D : 3 enchères de 105, 115 et 90 euros
** E : 3 enchères de 132, 135 et 140 euros
       
* L'acheteur E remporte les enchères et devra s'aquitter de 130 euros pour obtenir l'objet mis en vente.
        
### Objectif
L’objectif est de réaliser une applicaiton permettant de déterminer l’acheteur ayant remporté l’enchère ainsi que le prix de cette dernière.
 
