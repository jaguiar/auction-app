package org.yaourtcorp.auction
package ordering

object PriceBuyerTupleOrdering extends Ordering[(Int, String)] {
	//we could have done directly reverse ordering by reverting tuple1 and tuple2, but it could lead to misunderstanding, and Ordering has a "native reverse method" anyway
	def compare(tuple1:(Int, String), tuple2:(Int, String)) = {
		val compareValue = tuple1._1 compare tuple2._1
				compareValue match {
				case 0 => tuple1._2 compare tuple2._2
				case _ => compareValue
		}
	}
}