package mx.databeetle.scrapping.integration

import spock.lang.Specification
import java.lang.Void as Should
import mx.databeetle.scrapping.impl.HtmlDocument

class HTMLInformationExtractorSpec extends Specification {
  
  HtmlDocument document
  
  def setup(){
    String url = "http://www.jornada.unam.mx/ultimas/2018/01/23/fepade-solo-investigara-desvio-a-favor-de-un-partido-en-chihuahua-2772.html"
    String content = new URL(url).text
    document = new HtmlDocument(content,"body","div.related_nitf")
  }

  Should "get the related links for the new"(){
    when:
      ArrayList<String> links = document.getDocumentLinks()
    then:
      links.size() == 6
  }

}
