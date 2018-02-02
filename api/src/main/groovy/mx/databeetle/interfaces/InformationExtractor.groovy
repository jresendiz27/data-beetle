package mx.databeetle.interfaces

import groovy.transform.CompileStatic

@CompileStatic
interface InformationExtractor {
  Map<String, Long> getWordsCount()
}
