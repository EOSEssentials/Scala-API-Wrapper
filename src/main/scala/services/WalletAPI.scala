package services

import play.api.libs.json.JsValue
import utils.Settings
import utils.Client.{get, post, postRaw}

import scala.concurrent.Future

object WalletAPI extends Settings {

  private def route(r:String):String = eosSettings.uri(s"/wallet/$r")

  def create(name:String):Future[Option[String]] =
    postRaw(route("create"), name)

  def open(name:String):Future[Option[String]] =
    postRaw(route("open"), name)

  def lock(name:String):Future[Option[String]] =
    postRaw(route("lock"), name)

  def lockAll:Future[Option[JsValue]] =
    get(route("lock_all"))

  def unlock(name:String, privateKey:String): Future[Option[String]] =
    postRaw(route("unlock"), s"[$name, $privateKey]")

  def importKey(name:String, privateKey:String): Future[Option[String]] =
    postRaw(route("import_key"), s"[$name, $privateKey]")

  def list: Future[Option[JsValue]] =
    get(route("list_wallets"))

  def listKeys: Future[Option[JsValue]] =
    get(route("list_keys"))

  def getPublicKeys: Future[Option[JsValue]] =
    get(route("get_public_keys"))

  def setTimeout(timeout:Long): Future[Option[String]] =
    postRaw(route("set_timeout"), timeout.toString)

  //TODO
  /*
  $ curl http://localhost:8889/v1/wallet/sign_transaction -X POST -d '[{"ref_block_num":21453,"ref_block_prefix":3165644999,"expiration":"2017-12-08T10:28:49","scope":["initb","initc"],"read_scope":[],"messages":[{"code":"currency","type":"transfer","authorization":[{"account":"initb","permission":"active"}],"data":"000000008093dd74000000000094dd74e803000000000000"}],"signatures":[]}, ["EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV"], ""]'
Example wallet_sign_trx Result
{
  "ref_block_num": 21453,
  "ref_block_prefix": 3165644999,
  "expiration": "2017-12-08T10:28:49",
  "scope": [
    "initb",
    "initc"
  ],
  "read_scope": [],
  "messages": [
    {
      "code": "currency",
      "type": "transfer",
      "authorization": [
        {
          "account": "initb",
          "permission": "active"
        }
      ],
      "data": "000000008093dd74000000000094dd74e803000000000000"
    }
  ],
  "signatures": [
    "1f393cc5ce6a6951fb53b11812345bcf14ffd978b07be386fd639eaf440bca7dca16b14833ec661ca0703d15e55a2a599a36d55ce78c4539433f6ce8bcee0158c3"
  ]
}
   */
  def signTrx = ???

}
