How to run

```
$ docker run -it --rm -p 8080:8080 -v `pwd`/wiremock:/home/wiremock rodolpheche/wiremock
$ cd webapp
$ npm install && npm start
```

Alternatively:

```
$ docker-compose up
```

Visit http://localhost:3000
