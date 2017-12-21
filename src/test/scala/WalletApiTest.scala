import org.scalatest.{AsyncFlatSpec, Matchers}
import services.WalletAPI
import TestingHelpers.assertNonEmptyStringResponseWithLogger
import TestingHelpers.assertNonEmptyJsonResponseWithLogger
import utils.Logger

class WalletApiTest extends AsyncFlatSpec with Matchers {
  val testWalletName: String = s"test-wallet-${Math.abs(scala.util.Random.nextInt*scala.util.Random.nextInt)}"
  var privateKey:String = ""
  val testKeyFromDocs = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3"
  Logger.debug("testWalletName: " + testWalletName)

  "WalletAPI" should "be able to create a wallet" in { WalletAPI.create(testWalletName) map {x => privateKey = x.getOrElse(""); x} }
           it should "be able to open a wallet" in { WalletAPI.open(testWalletName) map {x => x} }
           it should "be able to lock a wallet" in { WalletAPI.lock(testWalletName) map {x => x} }
           it should "be able to lock all wallets" in { WalletAPI.lockAll map {x => x} }
           it should "be able to unlock a wallet" in { WalletAPI.unlock(testWalletName, privateKey) map {x => x} }
           it should "be able to import a key into a wallet" in { WalletAPI.importKey(testWalletName, testKeyFromDocs) map {x => x} }
           it should "be able to list wallets" in { WalletAPI.list map {x => x} }
           it should "be able to list wallet keys" in { WalletAPI.listKeys map {x => x} }
           it should "be able to list all wallet public keys" in { WalletAPI.getPublicKeys map {x => x} }
           it should "be able to set a timeout for wallets" in { WalletAPI.setTimeout(10) map {x => x} }
}
