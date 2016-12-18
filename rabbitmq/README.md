Run:

```
$ docker run -d --hostname my-rabbit --name some-rabbit -p 4369:4369 -p 5671:5671 -p 5672:5672 -p 15671:15671 -p 8080:15672 rabbitmq:3-management
$ mvn clean package
$ java -jar target/rabbitmq.jar producer
$ java -jar target/rabbitmq.jar consumer 1
$ java -jar target/rabbitmq.jar consumer 2
$ java -jar target/rabbitmq.jar consumer 2
```
Connect to localhost:8080 with user guest:guest for admin console

To send message from perl:
```
docker run --link some-rabbit:rabbitmq -it --rm --name rabitmq-perl -v `pwd`:/usr/src/rabbitmq -w /usr/src/rabbitmq cpollet/perl-rabbitmq perl generate.pl
```
