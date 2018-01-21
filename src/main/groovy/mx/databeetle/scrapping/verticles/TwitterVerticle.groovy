package mx.databeetle.scrapping.verticles

import groovy.util.logging.Log4j2
import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject
import mx.databeetle.core.services.TwitterService

@Log4j2
class TwitterVerticle extends AbstractVerticle {
    TwitterService twitterService

    void start() {
        twitterService = new TwitterService()
        vertx.setPeriodic(120000, { handler ->
            twitterService.getInformationFromSource().each { url ->
                // TODO use ENUM instead of raw addresses
                vertx.eventBus().send("SEND_URL_TO_MESSAGE_BROKER", new JsonObject()
                        .put("body", new JsonObject().put("url", url))
                        .put("properties", new JsonObject().put("contentType", "application/json")))
            }
        })
    }
}
