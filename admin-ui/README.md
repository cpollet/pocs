How to run

```
$ docker run -it --rm -p 8080:8080 -v `pwd`/wiremock:/home/wiremock rodolpheche/wiremock
$ cd webapp
$ npm install && npm start
```

Visit http://localhost:3000

You can change the language by calling switchLanguage() from the browser console.