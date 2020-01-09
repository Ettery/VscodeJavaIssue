# Demonstrate the issue with VSCode Java/Maven project

This is a simple Java Dropwizard project.  When run it hosts an http server with an endpoint [http://localhost:8080/api/hello](http://localhost:8080/api/hello) which responds with **Hello world!**

This project builds (assuming you have Java and Maven setup) - with:

    mvn clean install

And the jar file runs - copy the output from /target to /bin and run `rest-start.sh`

So it is a healthy Java Maven project.

However, when I open it in VSCode (I have the Java Extension pack installed) - I see 105 "Problems" reported in the pom.xml - 
* 4x "Failed to read artifacto descriptor for ..."
* The rest "Missing artifact ..."


This appears to prevent most of the VSCode Java tools from working - I can't build and debug.  Intellisense seems to be sketchy/intermittent.  And so on.