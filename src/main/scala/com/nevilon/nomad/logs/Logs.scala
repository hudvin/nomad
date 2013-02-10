package com.nevilon.nomad.logs

import org.apache.log4j.LogManager

/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 2/10/13
 * Time: 6:40 AM
 */

trait Logs {

  private[this] val logger = LogManager.getLogger(this.getClass.getName)


  import org.apache.log4j.Level._

  def debug(message: => String) = if (logger.isEnabledFor(DEBUG)) logger.debug(message) else Unit

  def debug(message: => String, ex: Throwable) = if (logger.isEnabledFor(DEBUG)) logger.debug(message, ex) else Unit

  def debugValue[T](valueName: String, value: => T): T = {
    val result: T = value
    debug(valueName + " == " + result.toString)
    result
  }

  def info(message: => String) = if (logger.isEnabledFor(INFO)) logger.info(message) else Unit

  def info(message: => String, ex: Throwable) = if (logger.isEnabledFor(INFO)) logger.info(message, ex) else Unit

  def warn(message: => String) = if (logger.isEnabledFor(WARN)) logger.warn(message) else Unit

  def warn(message: => String, ex: Throwable) = if (logger.isEnabledFor(WARN)) logger.warn(message, ex) else Unit

  def error(ex: Throwable) = if (logger.isEnabledFor(ERROR)) logger.error(ex.toString, ex) else Unit

  def error(message: => String) = if (logger.isEnabledFor(ERROR)) logger.error(message) else Unit

  def error(message: => String, ex: Throwable) = if (logger.isEnabledFor(ERROR)) logger.error(message, ex) else Unit

  def fatal(ex: Throwable) = if (logger.isEnabledFor(FATAL)) logger.fatal(ex.toString, ex) else Unit

  def fatal(message: => String) = if (logger.isEnabledFor(FATAL)) logger.fatal(message) else Unit

  def fatal(message: => String, ex: Throwable) = if (logger.isEnabledFor(FATAL)) logger.fatal(message, ex) else Unit
}
