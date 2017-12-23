import org.nsjames.contracts.CurrencyContract
import org.nsjames.models.MessageBuilder
import org.nsjames.services.EOSApis
import org.scalatest.{AsyncFlatSpec, Matchers}
import play.api.libs.json.Json
import org.nsjames.utils.{Client, Logger}

class IntegrationTest extends AsyncFlatSpec with Matchers {

  // IMPORTANT: This test requires the "Currency" contract to exist on your endpoint.


  val eos = EOSApis

  // A wallet will be exported to the console on first run, once it has been generated
  // switch to `true` and fill the variables below
  val walletGenerated:Boolean   = false
  val generatedWalletName       = "test-wallet-2126438256"
  val generatedWalletPassword   = "PW5K5JxPUGpoE6u9TbN1yWhS63pgE8WjknmjiwPZ7dRv1VM4j3KvL"

  val walletName:String         = if(walletGenerated) generatedWalletName else s"test-wallet-${Math.abs(scala.util.Random.nextInt * scala.util.Random.nextInt)}"
  var walletPassword:String     = generatedWalletPassword

  // From docs
  val initaPrivateKey:String    = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3"

  //Using manually created keys for now until [eos-scala-ecc] is done
  val ownerPrivate              = "5J5Eao8XbmWWw5kEoi6hEBrjCf3NVi4Nrxtx7smAeUwRG2Ar1Vs"
  val ownerPublic               = "EOS7ktDmFnEXURGfu4wux8qwexPtA7b5kMSn1EBf2gsoG7UJ1a2RR"
  val activePrivate             = "5JFr2KoXsuq1oykWCvGiMinQta6P5KJwLuakCJZVk5kjpmLitGX"
  val activePublic              = "EOS6DpbXT7PujPqD9NSZby84oq5gnmYrLZiGsiiJewynHCNkyo9fo"

  if(!walletGenerated) "WalletAPI" should "be able to create a wallet with a given name" in {
    eos.wallet.create(walletName) map { password =>
      walletPassword = password
      Logger.block(List(s"Wallet $walletName | $password"))
      assert(walletPassword.nonEmpty)
    }
  }

  else "wallet" should "be able to open and unlock a pre-generated wallet" in {
    for {
      opened <- eos.wallet.open(walletName)
      unlocked <- eos.wallet.unlock(walletName, walletPassword)
    } yield assert(opened && unlocked)
  }

  it should "be able to import the inita private key into the wallet" in {
    eos.wallet.importKey(walletName, initaPrivateKey) map { result => assert(result) }
  }

  it should "be able to import the active private key into the wallet" in {
    eos.wallet.importKey(walletName, activePrivate) map { result => assert(result) }
  }

  "EOSApis" should "be able to create, sign, and push a `transfer` message using a single method" in {
    val transfer = CurrencyContract.Transfer("currency", "inita", 10)
    eos.sendMessage(MessageBuilder(transfer.message, Json.toJson(transfer), transfer.scopes, activePublic)) map { trx =>
      Logger.debug("Transaction: " + trx)
      assert(1==1)
    }
  }

  it should "be able to create, sign, and push multiple `transfer` messages using a single method" in {
    val messages = List(
      MessageBuilder(CurrencyContract.Transfer("currency", "inita", 11).message, Json.toJson(CurrencyContract.Transfer("currency", "inita", 10)), List("currency","inita"), activePublic),
      MessageBuilder(CurrencyContract.Transfer("currency", "initb", 13).message, Json.toJson(CurrencyContract.Transfer("currency", "initb", 10)), List("currency","initb"), activePublic),
      MessageBuilder(CurrencyContract.Transfer("currency", "initc", 15).message, Json.toJson(CurrencyContract.Transfer("currency", "initc", 10)), List("currency","initc"), activePublic)
    )
    eos.sendMessages(messages) map { trxs =>
      Logger.debug("Successful transactions: " + trxs.succeeded)
      Logger.debug("Failed transactions: " + trxs.failed)
      assert(1==1)
    }
  }



}
