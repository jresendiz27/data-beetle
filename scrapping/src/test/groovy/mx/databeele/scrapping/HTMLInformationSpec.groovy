package mx.databeetle.scrapping

import groovy.util.logging.Log4j2
import mx.databeetle.scrapping.impl.HtmlDocument
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
@Log4j2
class HTMLInformationSpec extends Specification {

  HtmlDocument document

  def setup() {
    String html = this.getClass().getClassLoader().getResource('./pages/BasicPage.html').text
    document = new HtmlDocument(html, "body", "body")
  }

  void "Extract the word count from the page"() {
    given:
      document.removeUnusedDOMElements()
      Map<String, Long> wordCount = document.getWordsCount()

    expect:
      !wordCount.isEmpty()
      wordCount.get("world") == (Long) 5
  }


  void "Get SEO meta-tags as Map"() {
    expect:
      assert true // Implement me!
  }
}
