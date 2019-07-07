# webzip

WebZip is a Tomcat web application to zip and unzip archives. 

## Prerequisites

- Git
- Java
- Maven
- Tomcat

## Summary
WebZip is a Tomcat web application to zip and unzip archives. Version 1.0 provides a REST API. Access is secured by an API token. Zip files are manipulated on the server side within a configurable folder. Each API token has an associated folder. Applications using WebZip need to clean up the content of their folder on the server.

## Setup
Place the webzip.war file into the Tomcat webapps folder. The auto-deploy feature of Tomcat will exctract the war file.

Open webzip/META-INF/webzip.properties and configure the location of the server folder.

By setting the overwritefiles property you can control if existing files or archive should be overwritten. If set to false, an error will be thrown in these cases.

Open webzip/META-INF/servicekeys.json and add service key entries as needed.

## REST API Reference
Open WebZip in a browser by navigating to http://servername:port/webzip to view the API documentation.
