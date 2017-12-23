import services.{EOSApis, WalletAPI}
import utils.Logger
import org.scalatest.{AsyncFlatSpec, Matchers}
import TestingHelpers.booleanToAssertion
import play.api.libs.json.JsValue

class WalletApiTest extends AsyncFlatSpec with Matchers {
  val testWalletName: String = s"test-wallet-${Math.abs(scala.util.Random.nextInt*scala.util.Random.nextInt)}"
  var testWalletPass: String = ""
  val testKeyFromDocs = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3"
  Logger.debug("testWalletName: " + testWalletName)

  "WalletAPI" should "be able to create a wallet" in { WalletAPI.create(testWalletName).map(x => {testWalletPass = x; x}) map {_.isInstanceOf[String]} }
           it should "be able to open a wallet" in { WalletAPI.open(testWalletName) map {_.isInstanceOf[Boolean]} }
           it should "be able to lock a wallet" in { WalletAPI.lock(testWalletName) map {_.isInstanceOf[Boolean]} }
           it should "be able to lock all wallets" in { WalletAPI.lockAll map {_.isInstanceOf[Boolean]} }
           it should "be able to unlock a wallet" in { WalletAPI.unlock(testWalletName, testWalletPass) map {_.isInstanceOf[Boolean]} }
           it should "be able to import a key into a wallet" in { WalletAPI.importKey(testWalletName, testKeyFromDocs) map {_.isInstanceOf[Boolean]} }
           it should "be able to list wallets" in { WalletAPI.list map {_.isInstanceOf[List[String]]} }
           it should "be able to list wallet keys" in { WalletAPI.listKeys map {_.isInstanceOf[List[List[String]]]} }
           it should "be able to list all wallet public keys" in { WalletAPI.getPublicKeys map {_.isInstanceOf[List[String]]} }
           it should "be able to set a timeout for wallets" in { WalletAPI.setTimeout(10) map {_.isInstanceOf[String]} }
}
