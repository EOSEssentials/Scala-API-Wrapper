import contracts.CurrencyContract
import services.EOSApis
import org.scalatest.{AsyncFlatSpec, Matchers}
import play.api.libs.json.Json
import utils.Logger

class IntegrationTest extends AsyncFlatSpec with Matchers {

  // IMPORTANT: This test requires the "Currency" contract to exist on your endpoint.


  val eos = EOSApis

  // A wallet will exported to the console one first run, once it has been generated
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
    eos.wallet.create(walletName).map(_.getOrElse("")) map { password =>
      walletPassword = password
      Logger.block(List(s"Wallet $walletName | $password"))
      assert(walletPassword.nonEmpty)
    }
  }

  else "WalletAPI" should "be able to open and unlock a pre-generated wallet" in {
    for {
      opened <- eos.wallet.open(walletName).map(_.getOrElse(""))
      unlocked <- eos.wallet.unlock(walletName, walletPassword).map(_.getOrElse(""))
    } yield assert(opened == "{}" && unlocked == "{}")
  }

  it should "be able to import the inita private key into the wallet" in {
    eos.wallet.importKey(walletName, initaPrivateKey).map(_.getOrElse("")) map { result =>
      assert(result == "{}")
    }
  }

  it should "be able to import the active private key into the wallet" in {
    eos.wallet.importKey(walletName, activePrivate).map(_.getOrElse("")) map { result =>
      assert(result == "{}")
    }
  }

  "ChainAPI" should "be able to create, sign, and push a `transfer` message" in {
    val transfer = CurrencyContract.Transfer("currency", "inita", 10)
    eos.sendMessage(transfer.message, Json.toJson(transfer), List("currency","inita"), activePublic) map {
      case None => assert(1==2)
      case Some(trx) =>
        Logger.debug("Transaction: " + trx)
        assert(1==1)
    }
  }



}
