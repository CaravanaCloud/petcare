# petcare

Care for Pets :)

# Today we'll build

- Sample "Pet Care" application
- Service-oriented
- Secure from the start
- Multi-module

Featuring:
- Quarkus
- Keycloak

# Create root dir
```
mkdir petcare
```
or git clone ...

# Create "core" module
```
quarkus create cli petcare:ptc-core:0.0.1 \
    --java=17 \
    --package-name=petcare.core \
    --help

cd ptc-core
quarkus dev
```
- Hello World Quarkus

# Create "api" module
```
quarkus create app petcare:ptc-api:0.0.1 \
    --java=17 \
    --package-name=petcare.api \
    --help

mvn -f ptc-api/pom.xml quarkus:dev
```
- Hello World JAX-RS

# Create "app" module
```
gh repo clone vaadin/base-starter-flow-quarkus

mv base-starter-flow-quarkus ptc-app

mvn -f ptc-app/pom.xml quarkus:dev
```
- Hello World Vaadin

# Add root pom
```
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <modelVersion>4.0.0</modelVersion>
    <groupId>petcare</groupId>
    <artifactId>petcare</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <modules>
        <module>ptc-core</module>
        <module>ptc-api</module>
        <module>ptc-app</module>
    </modules>
    <build>
        <defaultGoal>clean install</defaultGoal>
    </build>
</project>
```
- Set names and properties on child POMs 
- Open/Refresh your IDE
- Build all
```
mvn
```
# Adding JSON
```
cd ptc-api
quarkus ext add resteasy-json
```

# Adding Database DevServices to API
```
cd ptc-api
quarkus ext add agroal
quarkus ext add jdbc-mysql
quarkus ext add hibernate-jpa-orm
quarkus ext add hibernate-orm-panache
```

# Adding Flyway
```
quarkus ext add flyway
```

```
    <dependency>
      <groupId>org.flywaydb</groupId>
      <artifactId>flyway-mysql</artifactId>
    </dependency>
```
# Adding Keycloak DevServices to API
Add OpenID Connect and Keycloak extensions
```
cd ptc-api
quarkus ext add oidc
quarkus ext add keycloak-authorization
```
See: application-dev.properties

try
```
curl --location --request POST 'http://localhost:13370/realms/petcare-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=alice' \
--data-urlencode 'password=Masterkey123' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=quarkus-app' \
--data-urlencode 'client_secret=secret'  | jq

```

# Create protected resource
See: WhoAmIResource
```
export ACCESS_TOKEN=$(curl -s --location --request POST 'http://localhost:13370/realms/petcare-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=alice' \
--data-urlencode 'password=Masterkey123' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=quarkus-app' \
--data-urlencode 'client_secret=secret'  | jq -r '.access_token')

export ACCESS_HEADER="Authorization: Bearer $ACCESS_TOKEN"

curl -v --location \
    --request GET 'http://localhost:13371/api/user/whoami' \
    --header "$ACCESS_HEADER"

```

# Setup REST Client
```
cd ptc-app
quarkus ext add rest-client
quarkus ext add oidc
quarkus ext add oidc-client
quarkus ext add keycloak-authorization
```
```
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-rest-client-jackson</artifactId>
</dependency>
```


# Create service interface
See: WhoAmIService