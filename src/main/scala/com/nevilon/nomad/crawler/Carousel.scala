/**
 * Copyright (C) 2012-2013 Vadim Bartko (vadim.bartko@nevilon.com).
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * See file LICENSE.txt for License information.
 */
package com.nevilon.nomad.crawler

import com.nevilon.nomad.logs.Logs
import concurrent._
import scala.util.Try
import scala.Some
import collection.mutable

class Carousel(val maxThreads: Int, dataProvider: PopProvider) extends Logs {

  private var futures = new mutable.HashSet[Future[Unit]] with mutable.SynchronizedSet[Future[Unit]]

  private var onStartMethod: (Url) => Unit = null
  private var onBeforeStart: (Url) => Unit = null
  private var onCrawlingComplete: () => Unit = null

  private var canWork = true

  private val sync = new Object



  // fw.close()

  private val t = new Thread() {

    override def run() {

      sync.synchronized {
        var flag = true
        while (flag) {

          if (canWork) {

            while (futures.size < maxThreads && canWork) {
              dataProvider.pop() match {
                case None => {
                  info("sorry, no links to crawl ")
                  if (futures.isEmpty) {
                    canWork = false
                  } else {
                    sync.wait()
                  }
                }
                case Some(url) => {
                  require(onBeforeStart!=null)
                  onBeforeStart(url)
                  futures += buildFuture(url)
                  info("starting future for crawling " + url.location)
                }
              }
            }


            if (canWork) sync.wait()
          } else if (!canWork) {
            if (futures.nonEmpty) sync.wait()
            else {
              onCrawlingComplete()
              flag = false
            }
          }
        }
      }

    }

  }

  t.start()

  def stop(softly: Boolean) {
    canWork = false
    if (!softly) {
      t.interrupt()
    }
  }


  private def buildFuture(url: Url): Future[Unit] = {
    implicit val ec = ExecutionContext.Implicits.global
    val thisFuture = future {
      onStartMethod(url)
    }
    thisFuture.onComplete((data: Try[Unit]) => ({
      futures -= thisFuture
      sync.synchronized {
        sync.notify()
      }
    }))
    thisFuture
  }

  def setOnStart(method: (Url) => Unit) {
    onStartMethod = method
  }

  def setOnBeforeStart(method: (Url) => Unit) {
    onBeforeStart = method
  }

  def setOnCrawlingComplete(method: () => Unit) {
    onCrawlingComplete = method
  }

}
