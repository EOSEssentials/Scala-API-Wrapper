package org.nsjames.utils

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class EOSApiException(private val message: String = "",
                      private val cause: Throwable = None.orNull)
                      extends Exception(message, cause)

object Errors {
  implicit def futureEitherError(msg:String):Future[Right[Nothing, String]] = Future(Right(msg))
  implicit def eitherError(msg:String):Right[Nothing, String] = Right(msg)
}
