package mx.databeetle.core.api

import groovy.transform.CompileStatic

@CompileStatic
interface HtmlInformation extends DocumentInformation {
    Map<String, String> getMetaTagsInformation()
    void removeUnusedDOMElements()
    ArrayList<String> getDocumentLinks()
}
