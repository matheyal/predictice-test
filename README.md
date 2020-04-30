#  Predictice technical test

### :telescope: Demo
![alt text](screenshot/1.png)
![alt text](screenshot/2.png)
![alt text](screenshot/3.png)
![alt text](screenshot/4.png)

### Requirements

Before you started using this project make sure you have installed this programs in your computer:

* [Java](https://www.oracle.com/java/) - is the world’s #1 programming language.
* [Node.js](https://nodejs.org/en/) - Node.js® is a JavaScript runtime.
* [Maven](https://maven.apache.org/) - is a software project management and comprehension tool.
* [H2 Database](http://h2database.com) - the Java SQL database.
* [Elasticsearch](https://www.elastic.co/elasticsearch/) - a RESTful search engine
* [Docker](https://www.docker.com/) - The famous containerization software


### Running the project locally

#### Elasticsearch

To run this project, you need a running instance of Elasticsearch. The easiest is to run it with docker:

```sh
docker run -d \
  --name elasticsearch \
  -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  --mount source=elasticsearch-data,target=/usr/share/elasticsearch/data \
  docker.elastic.co/elasticsearch/elasticsearch:7.5.2
```

You can then query the instance at http://localhost:9200

#### Loading sample data into Elasticsearch

This app is quite boring without a decent amount of albums stored into elasticsearch.

Don't worry I've got you covered! 

Once you have a local instance of elasticsearch running, simply run this from the root of the repo:
```sh
./load-albums.py
```

#### Backend

For the backend, simply run the following:
```sh
./mvnw -pl backend-spring spring-boot:run
```

#### Frontend

For the frontend, run the following:
```sh
cd frontend-vue
npm install
npm run dev
```

Once everything is up and running, go to http://localhost:4200/ and enjoy!
