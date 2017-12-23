package org.nsjames.models

import play.api.libs.json.Json

case class Key(key:String, weight:Long)
object Key { implicit val format = Json.format[Key] }

case class RequiredAuth(threshold:Long, keys:List[Key], accounts:List[String])
object RequiredAuth { implicit val format = Json.format[RequiredAuth] }

case class Permission(perm_name:String, parent: String, required_auth:RequiredAuth)
object Permission { implicit val format = Json.format[Permission] }

case class Account(account_name:String, eos_balance:String, staked_balance:String, unstaking_balance:String, last_unstaking_time:String,permissions:List[Permission])
object Account { implicit val format = Json.format[Account] }
