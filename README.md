# Wooder [![Build Status](https://travis-ci.org/lm2343635/Wooder.svg?branch=master)](https://travis-ci.org/lm2343635/Wooder)

Wooder is an i18n resource management system for mobile application development.
Without Wooder, the project managers, designers and developers should manage the i18n resources include strings and icons manually.
With Wooder, the project managers and designers can manage the i18n resources with a Web application,
and the developers can pull the i18n resources from the server to the IDE (Xcode and Android Studio) automatically.

## Run the Project

This project needs JDK 11, maven 3.6 and node.js 11.14.

```Shell
brew install maven
brew install node
npm install -g @vue/cli
```

Run the backend project with maven.

```Shell
mvn package
mvn --projects backend spring-boot:run
```

Debug mode for the frontend module.

```Shell
cd frontend/
npm run serve
```
