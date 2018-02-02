package mx.databeetle.scrapping.verticles

import groovy.util.logging.Log4j2
import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject
import io.vertx.rabbitmq.RabbitMQClient
import io.vertx.rabbitmq.RabbitMQOptions

@Log4j2
class RabbitMQVerticle extends AbstractVerticle {
    static String INCOME_URLS = "income_url_queue"
    RabbitMQClient client

    void start() {
        RabbitMQOptions rabbitMQOptions = new RabbitMQOptions()
        // rabbitMQOptions.uri = "amqp://bbofzllq:aYr9P_WTYo7SmwLj6fuzfedosZ-4jFfG@elephant.rmq.cloudamqp.com/bbofzllq"

        /*
        Host(s)
elephant-01.rmq.cloudamqp.com
User & Vhost
Password
        * */

        rabbitMQOptions.user = "bbofzllq"
        rabbitMQOptions.password = "aYr9P_WTYo7SmwLj6fuzfedosZ-4jFfG"
        rabbitMQOptions.host = "elephant.rmq.cloudamqp.com"
        rabbitMQOptions.virtualHost = "bbofzllq"

        client = RabbitMQClient.create(getVertx(), rabbitMQOptions)
        client.start({ resultHandler ->
            vertx.eventBus().consumer("SEND_URL_TO_MESSAGE_BROKER", { handler ->
                if (client.isOpenChannel()) {
                    JsonObject jsonObject = (JsonObject) handler.body()
                    client.basicPublish("", INCOME_URLS, jsonObject, { messageHandler ->
                        if (messageHandler.failed()) {
                            log.info "An insertion failed ... ${messageHandler.cause()}"
                            messageHandler.cause().printStackTrace()
                        }
                    })
                }
            })

            vertx.eventBus().consumer("RECEIVE_URL_FROM_MESSAGE_BROKER", { msg ->
                def json = msg.body()
                // TODO use another process to handle the url information
                Thread.sleep(15000)
                log.info("Processed url ... : ${json}")
            })

            client.basicConsume(INCOME_URLS, "RECEIVE_URL_FROM_MESSAGE_BROKER", { consumeResult ->
                if (consumeResult.succeeded()) {
                    log.info("RabbitMQ consumer created !")
                } else {
                    consumeResult.cause().printStackTrace()
                }
            })
        })
    }

    void stop() {
        log.info("TODO, implement me!")
    }
}
