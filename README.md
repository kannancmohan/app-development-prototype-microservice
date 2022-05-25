# app-development-prototype-microservice
    spring boot microservice implementation
## project initial setup
    Add supressions.xml for maven-checkstyle-plugin
    Add .mvn/jvm.config to fix issue with git-code-format-maven-plugin . check https://github.com/Cosium/git-code-format-maven-plugin
    Add lombok.config for lombok
    Add .gitignore
## Project IDE initial setup
    Add google-java-format plugin to intellij
    Add google code style for intellij from https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
    [Optional] Add spotbugs plugin https://plugins.jetbrains.com/plugin/14014-spotbugs
    [Optional] Add sonarlint plugin https://plugins.jetbrains.com/plugin/7973-sonarlint
    [Optional] Add GitToolBox plugin https://plugins.jetbrains.com/plugin/7499-gittoolbox

## Project setup 
    Add the following properties for genearting open-api client and server code 
    open-api-server-package
    open-api-client-package

## Build and Start the application
    Build application using 'mvn clean install'
    Run application using mvn spring-boot:run

## swagger ui endpoint
    http://localhost:8881/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
## spring actuator 
    http://localhost:8881/actuator
