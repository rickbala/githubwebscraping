## This project consists of 2 modules:

* webscraping -> contains the API main classes and interfaces
* githubanalyser -> a web-app made with Spring Boot that implements the API

### To see it live, go to:
[https://rickbalasgithubanalyser.herokuapp.com/](https://rickbalasgithubanalyser.herokuapp.com/)

### To run it locally:<br/>
`mvn clean install`<br/>
`cd githubanalyser`<br/>
`mvn spring-boot:run`<br/>

Then, access localhost:8080 in your browser.<br/>

### Or, to try it with Docker:<br/>
`sudo docker build -t rickbala/githubanalyser . && sudo docker run -p 8080:8080 rickbala/githubanalyser`

### Javadoc
To generate Javadoc, execute these commands:<br/>
`cd webscraping/ && mvn javadoc:javadoc`<br/>
`cd githubanalyser/ && mvn javadoc:javadoc`<br/>
