package mx.databeetle.scrapping.impl

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j2
import mx.databeetle.core.api.HtmlInformation
import mx.databeetle.core.sanitizer.StringSanitizer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.safety.Cleaner
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements

@Log4j2
@CompileStatic
class HtmlDocument implements HtmlInformation {

    Document document
    String content

    HtmlDocument(String content) {
        this.content = content
        this.document = new Cleaner(Whitelist.basic()).clean(Jsoup.parse(this.content))
    }

    @Override
    Map<String, Long> getWordsCount() {
        Map<String, Long> words = [:]
        // TODO use a selector considering the page information, based on interface?
        String bodyText = StringSanitizer.sanitizeContent(document.body().text())
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
        ["script", "style", "link", "title"].each { tag ->
            removeElements(document.getElementsByTag(tag))
        }
    }

    @Override
    ArrayList<String> getDocumentLinks() {
        ArrayList<String> links = []
        document.select("a")*.attr("href")
                .findAll { link -> link.startsWith("http") }
                .each { link -> links << link }
        links
    }

    static void removeElements(Elements elements) {
        elements.each {
            Element tagElement = (Element) it
            tagElement.remove()
        }
    }

}
