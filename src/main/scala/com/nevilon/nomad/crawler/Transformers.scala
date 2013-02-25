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

import com.tinkerpop.blueprints.Vertex


object Transformers {


  def vertex2Url(vertex: Vertex): Url = {
    //get status value
    val statusProperty = vertex.getProperty("status")
    val status = UrlStatus.withName(statusProperty.toString)
    //get action value
    //get str value of property by calling toString
    implicit def AnyRef2String(property: AnyRef) = property.toString
    new Url(vertex.getProperty("location"), status, vertex.getId,
      vertex.getProperty("fileId"))
  }

}
