## This project consists of 2 modules:

* webscraping -> contains the API main classes and interfaces
* githubanalyser -> a webapp made with Spring Boot that implements the API

### Requirements

* JDK 8
* Apache Maven 3+

### To see it live, go to
[https://rickbalasgithubanalyser.herokuapp.com/](https://rickbalasgithubanalyser.herokuapp.com/)

### To run it locally
`mvn clean install`<br/>
`cd githubanalyser/`<br/>
`mvn spring-boot:run`<br/>

### Or to try it with Docker
`mvn clean install && cd githubanalyser/`<br/>
`sudo docker build -t rickbala/githubanalyser . && sudo docker run -p 8080:8080 rickbala/githubanalyser`

Then, access localhost:8080 in your browser.

### Javadoc
To generate Javadoc, execute these commands:<br/>
`cd webscraping/ && mvn javadoc:javadoc`<br/>
`cd githubanalyser/ && mvn javadoc:javadoc`<br/>

### License
No licenses applied.
