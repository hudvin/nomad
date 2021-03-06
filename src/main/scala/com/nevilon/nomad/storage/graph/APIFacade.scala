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
package com.nevilon.nomad.storage.graph


/*
We do not track files with the same hash here!

 */
/*

class APIFacade extends SynchronizedDBService with FileStorage {


  //  def test() {
  //    val entities = findAllPdfFiles()
  //    entities.foreach(entity => {
  //      getIncoming(entity.url).foreach(u => println(u.location))
  //      getOutgoing(entity.url).foreach(t => println(t.location))
  //      println(entity)
  //      // println(entity.url + " " + entity.contentType + Transformers.vertex2Url(getUrl(entity.url).get).status)
  //      val path = FileSystems.getDefault().getPath("/tmp/pdfs/", System.currentTimeMillis().toString + ".pdf");
  //      Files.copy(getFileStream(entity.id), path)
  //    })
  //  }


  /*
    contentType
    domain
   */


  private implicit def DBObject2Entity(dbObject: DBObject) = {
    new Entity(
      dbObject.getAs[Long]("length").get,
      dbObject.getAs[String]("filename").get,
      new DateTime(dbObject.getAs[java.util.Date]("uploadDate").get.getTime),
      dbObject.getAs[ObjectId]("_id").get.toString,
      dbObject.getAs[String]("contentType").get,
      dbObject.getAs[String]("md5").get,
      dbObject.getAs[String]("urlId").get
    )
  }

  def findAllPdfFiles(): List[Entity] = {
    val entities = new ListBuffer[Entity]
    val q = ("length" $gt 100000) ++ ("contentType" -> "application/pdf")
    //val q = ("length" $gt 1)

    val result = getGridFS().files(q)
    result.foreach(obj => entities += obj)
    entities.toList
  }

  def getFileStream(fileId: String): InputStream = {
    getGridFS().findOne(new ObjectId(fileId)) match {
      case None => throw new RuntimeException("wrong fileId")
      case Some(gridFsFile) => gridFsFile.inputStream

    }
  }


  private def getConnectedUrls(url: String, direction: Direction): List[Url] = {
    getUrl(url) match {
      case None => List[Url]()
      case Some(v) => {
        val incoming = v.getVertices(direction, "relation").map(v => {
          Transformers.vertex2Url(v)
        })
        incoming.toList
      }
    }
  }

  def getIncoming(url: String): List[Url] = {
    getConnectedUrls(url, Direction.IN)
  }

  def getOutgoing(url: String): List[Url] = {
    getConnectedUrls(url, Direction.OUT)
  }


}


class Entity(val size: Long, val url: String,
             val timestamp: DateTime, val id: String,
             val contentType: String, val md5: String, val urlId: String) extends ToStringImpl

*/


