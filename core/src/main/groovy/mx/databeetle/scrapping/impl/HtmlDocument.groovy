package mx.databeetle.scrapping.impl

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j2
import mx.databeetle.interfaces.HtmlExtractor
import mx.databeetle.scrapping.sanitizer.StringSanitizer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.safety.Cleaner
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements


@Log4j2
@CompileStatic
class HtmlDocument implements HtmlExtractor {

    Document document
    ArrayList<Element> metaTags
    String content
    String newsSelector
    String relatedArticlesSelector

    HtmlDocument(String content) {
        this.content = content
        this.document = Jsoup.parse(this.content)
        this.metaTags = document.select("meta")
        this.document = new Cleaner(Whitelist.relaxed().addAttributes("div","class")).clean(this.document)
    }

    HtmlDocument(String content, String newsSelector, String relatedArticlesSelector) {
        this(content)
        this.newsSelector = newsSelector;
        this.relatedArticlesSelector = relatedArticlesSelector;
    }

    @Override
    Map<String, Long> getWordsCount() {
        Map<String, Long> words = [:]
        // TODO use a selector considering the page information, based on interface?
        String bodyText = StringSanitizer.sanitizeContent(document.select(newsSelector).text())
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
      Map<String, String> metaTags = [:]
      //TODO: Externalize allowed meta tags
      List<String> allowedMetaTags = ["keywords","robots","og:locale","og:url","og:title"]
      this.metaTags.findAll{ tag -> (tag.attr("name") in allowedMetaTags || tag.attr("property") in allowedMetaTags) }
                             .each{ tag -> metaTags[tag.attr("name")] = tag.attr("content") }
      metaTags
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
        document.select("$relatedArticlesSelector a")*.attr("href")
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
