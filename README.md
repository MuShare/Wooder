# Wooder

This project needs JDK 11 and maven 3.6 and node.js 11.14.

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
