package mx.databeetle.scrapping

import groovy.util.logging.Log4j2
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
    document = new HtmlDocument(htmlContent)
  }

  @Ignore
  Should "Read get the HTML file and extract the word count"() {

  }

  Should "Get the links from the HTML"(){
    given:
      document.removeUnusedDOMElements()
    when:
      ArrayList<String> links = document.getDocumentLinks()
    then:
      links.size() == 85
  }

  def cleanup() {

  }
}
