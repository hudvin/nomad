package com.nevilon.nomad.storage.graph

import com.tinkerpop.blueprints.Vertex
import collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 3/12/13
 * Time: 3:26 PM
 */

class Cache[T](load: (T) => Option[Vertex], create: (T) => Option[Vertex]) {

  private val cache = new mutable.HashMap[T, Vertex]

  def getOrElse(key: T): Option[Vertex] = {
    cache.get(key) match {
      case None => {
        load(key) match {
          case None => create(key)
          case Some(v) => {
            cache.put(key, v)
            Some(v)
          }
        }
      }
      case Some(v) => {
        Some(v)
      }
    }
  }

}