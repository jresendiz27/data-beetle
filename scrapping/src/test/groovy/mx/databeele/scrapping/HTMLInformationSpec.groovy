package mx.databeele.scrapping

import groovy.util.logging.Log4j2
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Cleaner
import org.jsoup.safety.Whitelist
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
@Log4j2
class HTMLInformationSpec extends Specification {
    HtmlDocumentInformation htmlInformation

    def setup() {

        String htmlContent = this.getClass().getClassLoader().getResource('./pages/BasicPage.html').text
        Document dirtyDoc = Jsoup.parse(htmlContent);
        Document cleanDoc = new Cleaner(Whitelist.basic()).clean(dirtyDoc)
        htmlInformation = new HtmlDocumentInformation(cleanDoc)
    }

    void "Extract the word count from the page"() {
        given:
        htmlInformation.removeUnusedDOMElements()
        Map<String, Long> wordCount = htmlInformation.getWordsCount()

        expect:
        !wordCount.isEmpty()
        wordCount.get("world") == (Long) 5
    }

    void "Get SEO meta-tags as Map"() {
        expect:
        assert true // Implement me!
    }
}
