package mx.databeetle.pojo

import mx.databeetle.interfaces.Resource
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.CompileStatic

@CompileStatic
@JsonSerialize
class News implements Resource {
  String url
  Set<String> links
  String content
  String stemmedContent
  Integer count
  Date dateCreated
  Date lastUpdated
  Set<String> sources
  HashMap<String, String> metaTags
  String latitude
  String longitude
}
