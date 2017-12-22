package utils

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Errors {
  implicit def futureEitherError(msg:String):Future[Right[Nothing, String]] = Future(Right(msg))
  implicit def eitherError(msg:String):Right[Nothing, String] = Right(msg)
}
