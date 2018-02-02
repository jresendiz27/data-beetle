package mx.databeetle.core.services

import groovy.util.logging.Log4j2
import groovyx.net.http.ContentTypes
import groovyx.net.http.HttpBuilder
import groovyx.net.http.NativeHandlers
import org.codehaus.groovy.runtime.EncodingGroovyMethods

import static java.nio.charset.StandardCharsets.UTF_8

@Log4j2
class TwitterService {
    // TODO retrieve the information from external variables or database
    final static String TWITTER_CONSUMER_KEY = "u6qu9YMMHyR733hrSlOcNnpuP"
    final static String TWITTER_PRIVATE_KEY = "gj78IyRq94PGrrb4xvk6DpPVcKNVC8fct9I6p5CeWUSrl34Qsc"
    final static String TWITTER_BASE_API_URL = "https://api.twitter.com"
    String accessToken
    final static List<String> HASH_TAGS = [
            "#morena",
            "#elecciones",
            "#meade",
            "#pan",
            "#prd",
            "#pri",
            "#amlo",
            "#elecciones2018",
            "#movimientociudadano",
            "#anaya",
            "#bronco",
            "#movimientonaranja",
            "rusia"]
    HttpBuilder httpBuilder

    TwitterService() {
        httpBuilder = HttpBuilder.configure {
            request.uri = TWITTER_BASE_API_URL
            request.contentType = ContentTypes.URLENC[0]
        }
    }

    private String retrieveAccessToken() {
        String encodedKeys = EncodingGroovyMethods.encodeBase64((byte[]) "$TWITTER_CONSUMER_KEY:$TWITTER_PRIVATE_KEY".bytes)

        def result = httpBuilder.post {
            request.uri.path = "/oauth2/token"
            request.headers = [
                    'Authorization': "Basic ${encodedKeys}"
            ]
            request.body = "grant_type=client_credentials"
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
        }

        return result.access_token
    }


    Set<String> getInformationFromSource() {
        accessToken = retrieveAccessToken()

        Set<String> information = new HashSet<>()
        HASH_TAGS.each { hashTag ->
            Map result = (Map) httpBuilder.get {
                request.headers = [
                        'Authorization': "Bearer ${accessToken}"
                ]
                request.uri.path = '/1.1/search/tweets.json'
                request.uri.query = [
                        q         : hashTag + " filter:links",
                        resultType: 'recent'
                ]
            }
            result.statuses.each { info ->
                ((List<String>) info.text.split(" ")).each { word ->
                    if (word.startsWith("http")) {
                        try {
                            URL validateUrl = new URL(word)
                            validateUrl.toURI()
                            information.add(new String(word.bytes, UTF_8))
                        } catch (Exception e) {
                            log.error "Not valid URL $word"
                        }
                    }
                }

            }
        }
        log.info information
        return information
    }

}
