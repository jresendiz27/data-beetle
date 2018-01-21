package mx.databeetle.interfaces

import groovy.transform.CompileStatic

@CompileStatic
interface HtmlExtractor extends InformationExtractor {
  Map<String, String> getMetaTagsInformation()
  void removeUnusedDOMElements()
  ArrayList<String> getDocumentLinks()
}
