```
$ docker-compose up
```

Grafana URL is: http://localhost:3000/
InfluxDB URL is: http://localhost:8083/

Then, insert some data:
```
curl -G http://localhost:8086/query --data-urlencode "q=CREATE DATABASE mydb"
curl -i -XPOST 'http://localhost:8086/write?db=mydb' --data-binary 'rate,context=net.cpollet.app1,user=cpol2009 value=2'
```

https://hub.docker.com/_/influxdb/ might help, as well...
