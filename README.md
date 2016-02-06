## Install Java 8

## Install Intellij IDEA from http://www.jetbrains.com/idea/

## Generate idea project
    # Mac user
    $ ./gradlew idea

    # Windows user 
    > gradlew.bat idea
    
## Install node and dependencies
    
### Install node
    # Mac user
    brew install node
    
    # Windows user
    http://nodejs.org/download/

### Install gulp
    $ npm install -g gulp
    
### Install node modules
    $ npm install

### Start asset watcher
    $ gulp


### Create a deployable jar
    $ ./gradlew capsule
    
### Start from the deployable jar
    $ java -Djetty.context.path="/urp" -Djetty.port=8080 -Djetty.access.log=/tmp/jetty.access.yyyy_mm_dd.log -Dlogback.configurationFile=src/main/resources/logback.xml -jar build/libs/jedurp.jar