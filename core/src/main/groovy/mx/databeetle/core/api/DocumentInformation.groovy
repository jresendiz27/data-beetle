package mx.databeetle.core.api

import groovy.transform.CompileStatic

@CompileStatic
interface DocumentInformation {
    Map<String, Long> getWordsCount();
}