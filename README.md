# quarkus-resteasy-vs-quarkus-resteasy-reactive

I have found people talking about this issue with the quarkus-rest-client-reactive but no one talking about the server side.
* [quarkus-rest-client-reactive does not URL encode some @QueryParam values](https://github.com/quarkusio/quarkus/issues/24426)

I have pushed [this](https://github.com/martincalvodaniel/quarkus-resteasy-vs-quarkus-resteasy-reactive) project to GitHub with the following two examples.

Any thoughts?

## quarkus-resteasy
As it is, the quarkus-resteasy server can be executed like this:
```shell
$ ./gradlew -p quarkus-resteasy quarkusDev                                                                                                                                                                                                                                                                                                                                                                                        ─╯

> Task :quarkus-resteasy:quarkusDev
Listening for transport dt_socket at address: 5005
Press [h] for more options>
Tests paused
Press [r] to resume testing, [h] for more options>
Press [r] to resume testing, [o] Toggle test output, [h] for more options>
 __             __             __      __   ___  __  ___  ___       __
/  \ |  |  /\  |__) |__/ |  | /__` __ |__) |__  /__`  |  |__   /\  /__` \ /
\__X \__/ /~~\ |  \ |  \ \__/ .__/    |  \ |___ .__/  |  |___ /~~\ .__/  |

                                              Powered by Quarkus 2.8.0.Final
2022-04-16 09:33:32,316 INFO  [io.quarkus] (Quarkus Main Thread) quarkus-resteasy 1.0-SNAPSHOT on JVM (powered by Quarkus 2.8.0.Final) started in 1.463s. Listening on: http://0.0.0.0:8080
2022-04-16 09:33:32,320 INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
2022-04-16 09:33:32,320 INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [cdi, resteasy, resteasy-jackson, smallrye-context-propagation, smallrye-openapi, swagger-ui, vertx
```
This server exposes the following simple endpoint:
```java
@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("queryParam") String queryParam) {
        return format("Hello RESTEasy CLASSIC. This was your received queryParam: %s", queryParam);
    }
}
```
If you query the server endpoint with `arg;123` as the queryParam value the server answers with the same value:
```shell
$ curl 'localhost:8080/hello?queryParam=arg;123'
Hello RESTEasy CLASSIC. This was your received queryParam: arg;123
```
If you query the server endpoint with `arg;123` url-encoded (`arg%3B123`)  as the queryParam value the server answers with the same no url-encoded value:
```shell
$ curl 'localhost:8080/hello?queryParam=arg%3B123'
Hello RESTEasy CLASSIC. This was your received queryParam: arg;123
```

## quarkus-resteasy-reactive
As it is, the quarkus-resteasy-reactive server can be executed like this:
```shell
$ ./gradlew -p quarkus-resteasy-reactive quarkusDev                                                                                                                                                                                                                                                                                                                                                                               ─╯

> Task :quarkus-resteasy-reactive:quarkusDev
Listening for transport dt_socket at address: 5005
Press [h] for more options>
Tests paused
Press [r] to resume testing, [h] for more options>
Press [r] to resume testing, [o] Toggle test output, [h] for more options>
 __             __             __      __   ___  __  ___  ___       __          __   ___       __  ___         ___
/  \ |  |  /\  |__) |__/ |  | /__` __ |__) |__  /__`  |  |__   /\  /__` \ / __ |__) |__   /\  /  `  |  | \  / |__
\__X \__/ /~~\ |  \ |  \ \__/ .__/    |  \ |___ .__/  |  |___ /~~\ .__/  |     |  \ |___ /~~\ \__,  |  |  \/  |___

                                                                                    Powered by Quarkus 2.8.0.Final
2022-04-16 09:32:27,178 INFO  [io.quarkus] (Quarkus Main Thread) quarkus-resteasy-reactive 1.0-SNAPSHOT on JVM (powered by Quarkus 2.8.0.Final) started in 1.437s. Listening on: http://0.0.0.0:8080
2022-04-16 09:32:27,182 INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
2022-04-16 09:32:27,183 INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [cdi, resteasy-reactive, resteasy-reactive-jackson, smallrye-context-propagation, smallrye-openapi, swagger-ui, vertx]
```
This server exposes the following simple endpoint:
```java
@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("queryParam") String queryParam) {
        return format("Hello RESTEasy REACTIVE. This was your received queryParam: %s", queryParam);
    }
}
```
If you query the server endpoint with `arg;123` as the queryParam value the server answers only with the content preceding the `;` character:
```shell
$ curl 'localhost:8080/hello?queryParam=arg;123'
Hello RESTEasy REACTIVE. This was your received queryParam: arg
```
If you query the server endpoint with `arg;123` url-encoded (`arg%3B123`)  as the queryParam value the server answers with the same no url-encoded value:
```shell
$ curl 'localhost:8080/hello?queryParam=arg%3B123'
Hello RESTEasy REACTIVE. This was your received queryParam: arg;123
```