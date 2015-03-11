package org.yaourtcorp.auction

import org.scalatest._
import org.scalatest.mock.MockitoSugar
/**
* @author jaguiar
*
*/
abstract class AbstractUnitSpec extends FlatSpec with Matchers with OptionValues with Inside with Inspectors with MockitoSugar {
}