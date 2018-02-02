package mx.databeetle.scrapping

import groovy.util.logging.Log4j2
import mx.databeetle.scrapping.impl.HtmlDocument
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Stepwise

import java.lang.Void as Should

@Stepwise
@Log4j2
class LaJornadaPageSpec extends Specification {
    HtmlDocument document

    def setup() {
        String htmlContent = this.getClass().getClassLoader().getResource('./pages/Jornada.html').text
        // div#other-articles selector for related articles section, loaded on runtime
        document = new HtmlDocument(htmlContent, "div#article-cont", "")
    }

    Should "Get SEO meta-tags as Map"() {
      when: 
        Map<String,String> meta = document.getMetaTagsInformation()
      then:
        meta["keywords"] == "noticias, México"
    }

    Should "Get the links from the HTML"() {
        given:
        document.removeUnusedDOMElements()
        when:
        ArrayList<String> links = document.getDocumentLinks()
        then:
        links.size() > 0
    }

    def cleanup() {

    }
}
