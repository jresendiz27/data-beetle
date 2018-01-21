package mx.databeetle.scrapping
import io.vertx.core.AbstractVerticle

class Main extends AbstractVerticle {

  @Override
  public void start(){
    vertx.setTimer(30000, { handler ->
      vertx.deployVerticle("mx.databeetle.scrapping.verticles.TwitterVerticle")
    })
    vertx.deployVerticle("mx.databeetle.scrapping.verticles.PostgisVerticle")
    vertx.deployVerticle("mx.databeetle.scrapping.verticles.ElasticSearchVerticle")
    vertx.deployVerticle("mx.databeetle.scrapping.verticles.RabbitMQVerticle")
  }
}
