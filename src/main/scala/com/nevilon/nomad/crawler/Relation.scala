package com.nevilon.nomad.crawler

import org.apache.commons.lang.builder.{HashCodeBuilder, EqualsBuilder}

/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 3/12/13
 * Time: 3:36 PM
 */

class Relation(val from: Url, val to: Url) {

  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[Relation]) {
      val other = obj.asInstanceOf[Relation]
      new EqualsBuilder()
        .append(from.location, other.from.location)
        .append(to.location, other.to.location) //add Action?
        .isEquals()
    } else {
      false
    }
  }

  override def hashCode(): Int = {
    new HashCodeBuilder()
      .append(from.location)
      .append(to.location) //add action?
      .toHashCode()
  }

}