package mx.databeetle.scrapping

import io.vertx.core.Verticle
import io.vertx.core.Vertx
import mx.databeetle.scrapping.verticles.RabbitMQVerticle

class Main {

    static void main(def args) {
        Vertx vertx = Vertx.vertx()
        vertx.setTimer(30000, { handler ->
            vertx.deployVerticle("mx.databeetle.scrapping.verticles.TwitterVerticle")
        })
        vertx.deployVerticle("mx.databeetle.scrapping.verticles.PostgisVerticle")
        vertx.deployVerticle("mx.databeetle.scrapping.verticles.ElasticSearchVerticle")
        vertx.deployVerticle("mx.databeetle.scrapping.verticles.RabbitMQVerticle")
    }
}
