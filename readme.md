# EOS RPC API for Scala

A basic Scala wrapper for the [EOS](https://github.com/EOSIO) rpc api. Under the [MIT License](https://raw.githubusercontent.com/nsjames/EOS-Scala-RPC-API/master/license)

*Note: This is currently a work in progress, once a working version is 
ready it will be added to maven to be sbt importable*

---

##Installation


**Getting the repo**

Add the sbt dependency to your `build.sbt` file.
```
"org.nsjames" %% "eos-scala-rpc-api" % "1.0-SNAPSHOT"
```
*Not on maven yet, but you may publish locally for now, you can clone and use `sbt publishLocal` for now*

---

**Configuration**

Create a new folder in your project's base directory called `conf`. Inside of that folder create a
configuration file called `application.conf` which should have the following properties.
```
httpClient {
  connectTimeout=5000
  readTimeout=5000
}

eos {
  // Set to some endpoint running eosd.  
  nodes = ["http://127.0.0.1:8888"]
}
```
*For more info on the `application.conf` file you can refer to the 
[lighbend config github](https://github.com/lightbend/config)*


In your `build.sbt` file add the following line to bring the `/conf/` directory into
your project.
```
resourceDirectory in Compile := baseDirectory.value / "conf"
```



## A few notes: 

* For ease of use, this library comes as a Singleton Object. 
You **do not** need to instantiate it or inject it as a dependency into your classes or tests.

* All methods will throw an easily catchable `EOSApiException`. 

* All methods can be accessed via the `EOSApis` object. Some methods (`sendMessage` and `sendMessages`)
are **only** accessible through the `EOSApis` object.

* All methods are asynchronous futures.


## Example usages

You can set a local variable for the `EOSApis` object for easier use, or you can use it as is.
```
val eos = EOSApis
eos.wallet.create()
--
or just
-- 
EOSApis.wallet.create()
```

**Create a new wallet**
```
EOSApis.wallet.create("my-new-wallet") map { password =>
    // If the creation is successful, you will be returned a password.
    // If not it will throw an exception with the error from the blockchain as a message.
    ...
}
```

**Open and unlock a wallet** ( example of catching throws )
```
try {
    for {
        opened <- EOSApis.wallet.open(walletName)
        unlocked <- EOSApis.wallet.unlock(walletName, walletPassword)
    } yield {
        // Wallet is now open, unlocked and ready for use
    }
} catch {
    case (e:EOSApiException) => 
        e.printStackTrace(); // Dont forget this. It's not nice to swallow.
        // Wallet opening or unlocking failed, check the trace for errors
}
```

**Sending transactions**

For custom contracts you will have to create a model to support it. The one below comes with
eosd and for example purposes also comes with this library.

For each action, you will need to create a case class to support it.

**Important: Always make sure your case classes have a companion object with an implicit
format for JSON parsing**
```
object CurrencyContract {
    // Each action in the contract will need a case class and a companion object
    case class Transfer(from:String, to:String, quantity:Long)
    object Transfer { implicit val format = Json.format[Transfer] }
}
```

Just for ease of use, i've added a few helper methods to the Transfer case class to convert it 
from an Action to a Method. **You do not have to do this**, but it's always a good idea to keep logic like this
on the model, and not in your controllers or services.
```
case class Transfer(from:String, to:String, quantity:Long){
    def message:Message = Message("currency", "transfer", List(MessageAuthorization(from, "active")))
    def scopes:List[String] = List(from, to)
}
```
Let's break those down for a second.
* The `Transfer` is the `data` that is sent to the contract.
* The `Message` is the `action` that is sent to the contract.
* `Scopes` are the accounts involved in this transfer action.

---

Now, we can instantiate a `Transfer` action and use it to send a message to the blockchain.

The `sendMessage` method does a few things. It converts your message into an unsigned transaction,
grabs the latest irreversible block off the chain, binds it to the transaction, uses the public key
supplied to sign the transaction, converts it to binary, and then sends it to the blockchain.
You can open up the `EOSApis` object to see how it's done.
```
try {
    val transfer = CurrencyContract.Transfer("currency", "inita", 10)
    val messageBuilder = MessageBuilder(transfer.message, Json.toJson(transfer), transfer.scopes, activePublicKeyUsedToCreateContract)
    eos.sendMessage(messageBuilder) map { transaction =>
        ...
    }
} catch { case (e:EOSApiException) =>  // Transaction failed. }
```

---

**Using the standalone apis**

You can also use each API on it's own.
```
ChainAPI.getBlock(blockNumOrID)
//or
EOSApis.chain.getBlock(blockNumOrID)
-----------------------------------------------
WalletAPI.lock(walletName)
//or
EOSApis.wallet.lock(walletName)
-----------------------------------------------
AccountHistoryAPI.getTransactions(accountName)
//or
EOSApis.accountHistory.getTransactions(accountName)

```



