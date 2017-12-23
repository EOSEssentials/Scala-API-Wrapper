package org.nsjames.utils

object StringUtils {
  def underToCamel(s:String, acc:String = ""):String = if(s.isEmpty) acc
    else if (s.take(1) == "_") underToCamel(s.slice(1, 2).head.toUpper + s.drop(2), acc)
    else underToCamel(s.drop(1), acc + s.take(1))

}
