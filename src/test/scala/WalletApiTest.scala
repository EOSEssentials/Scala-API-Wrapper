import org.scalatest.{AsyncFlatSpec, Matchers}
import services.WalletAPI
import TestingHelpers.assertNonEmptyStringResponseWithLogger
import utils.Logger

class WalletApiTest extends AsyncFlatSpec with Matchers {
  val testWalletId: Int = scala.util.Random.nextInt*scala.util.Random.nextInt
  Logger.debug("WalletID: " + testWalletId)

  "WalletAPI" should "be able to create a wallet" in { WalletAPI.create(s"test-wallet-$testWalletId") map {x => x} }
//           it should "be able to get a block" in { ChainAPI.getBlock("671239") map {x => assert(x.nonEmpty)} }
}
