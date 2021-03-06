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
package com.nevilon.nomad.filter

import groovy.lang.{GroovyObject, GroovyClassLoader}
import java.io.File
import com.nevilon.nomad.crawler.EntityParams


class GroovyEntityFilter(groovyFile: File) extends Filter[EntityParams] {

  private val engine = new GroovyFilterEngine[EntityParams]("filterEntity", groovyFile) {
    def mapArgs(t: EntityParams): List[AnyRef] = {
      List(t.size, t.url, t.mimeType).asInstanceOf[List[AnyRef]]
    }
  }


  def filter(entityParams: EntityParams) = engine.filter(entityParams)

}


class GroovyUrlFilter(groovyFile: File) extends Filter[String] {

  private val engine = new GroovyFilterEngine[String]("filterUrl", groovyFile) {
    def mapArgs(t: String): List[AnyRef] = {
      List[AnyRef](t)
    }
  }

  def filter(url: String) = engine.filter(url)

}

class GroovyDomainFilter(groovyFile: File) extends Filter[String] {

  private val engine = new GroovyFilterEngine[String]("filterDomain", groovyFile) {
    def mapArgs(t: String): List[AnyRef] = {
      List[AnyRef](t)
    }
  }

  def filter(domain: String) = engine.filter(domain)


}


abstract class GroovyFilterEngine[T](filterMethodName: String, groovyFile: File) {

  private var groovyObject: GroovyObject = null

  init()

  private def init() {
    val parent = getClass.getClassLoader
    val loader = new GroovyClassLoader(parent)
    val groovyClass = loader.parseClass(groovyFile)
    groovyObject = groovyClass.newInstance().asInstanceOf[GroovyObject]
  }

  def mapArgs(t: T): List[AnyRef]

  def filter(t: T): Option[Action.Action] = {
    val result = groovyObject.invokeMethod(filterMethodName, mapArgs(t).toArray[AnyRef]).asInstanceOf[Boolean]
    if (!result) Some(Action.Skip) else None
  }
}