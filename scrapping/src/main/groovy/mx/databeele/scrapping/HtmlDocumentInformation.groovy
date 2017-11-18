package mx.databeele.scrapping

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j2
import mx.databeetle.core.api.HtmlInformation
import mx.databeetle.core.sanitizer.StringSanitizer
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

@Log4j2
@CompileStatic
class HtmlDocumentInformation implements HtmlInformation {
    Document htmlDocument

    HtmlDocumentInformation(Document document) {
        this.htmlDocument = document
    }

    @Override
    Map<String, Long> getWordsCount() {
        Map<String, Long> words = [:]
        String bodyText = StringSanitizer.sanitizeContent(htmlDocument.body().text())
        bodyText.split(" ").each { String word ->
            if (words.get(word) != null) {
                Long wordQuantity = words.get(word) + 1
                words.put(word, wordQuantity)
            } else {
                words.put(word, (Long) 1)
            }
        }
        return words
    }

    @Override
    Map<String, String> getMetaTagsInformation() {
        return null
    }

    @Override
    void removeUnusedDOMElements() {
        removeElements(htmlDocument.getElementsByTag("script"))
        removeElements(htmlDocument.getElementsByTag("style"))
        removeElements(htmlDocument.getElementsByTag("link"))
        removeElements(htmlDocument.getElementsByTag("title"))
    }

    static void removeElements(Elements elements) {
        elements.each {
            Element tagElement = (Element) it
            tagElement.remove()
        }
    }
}
