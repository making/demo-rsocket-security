```
./mvnw clean package -DskipTests
```

Install [`rsc`](https://github.com/making) 0.7.0+ .

## Simple Authentication

```
java -jar target/demo-rsocket-security-0.0.1-SNAPSHOT.jar
```

Authentication per stream

```
$ rsc tcp://localhost:8888 --authSimple user:password -r hello -d World
Hello World, user!

# or

$ rsc tcp://localhost:8888 -u user:password -r hello -d World
Hello World, user!
```

Authentication per connection

```
$ rsc tcp://localhost:8888 --sm simple:user:password --smmt message/x.rsocket.authentication.v0 -r hello -d World
Hello World, user!

# or

$ rsc tcp://localhost:8888 --sm simple:user:password --smmt MESSAGE_RSOCKET_AUTHENTICATION -r hello -d World
Hello World, user!
```

## Bearer Authentication

This demo app uses [Pivotal Web Services](https://run.pivotal.io) as an Authentication Server.

[Create a PWS account](https://account.run.pivotal.io/z/uaa/sign-up).

```
java -jar target/demo-rsocket-security-0.0.1-SNAPSHOT.jar --spring.profiles.active=bearer
```

Retrieve token using [`cf`](https://github.com/cloudfoundry/cli) CLI.

```
cf api api.run.pivotal.io
cf auth <USERNAME> <PASSWORD>
TOKEN=$(cf oauth-token | sed 's/^bearer //')
```

Authentication per stream

```
$ rsc tcp://localhost:8888 --authBearer ${TOKEN} -r hello -d World
Hello World, <USERNAME>!
```

Authentication per connection

```
$ rsc tcp://localhost:8888 --sm bearer:${TOKEN} --smmt message/x.rsocket.authentication.v0 -r hello -d World
Hello World, <USERNAME>!

# or

$ rsc tcp://localhost:8888 --sm bearer:${TOKEN} --smmt MESSAGE_RSOCKET_AUTHENTICATION -r hello -d World
Hello World, <USERNAME>!
```

## (Legacy) Basic Authentication

```
java -jar target/demo-rsocket-security-0.0.1-SNAPSHOT.jar --spring.profiles.active=legacy
```

Authentication per stream

```
$ rsc tcp://localhost:8888 --authBasic user:password -r hello -d World
Hello World, user!
```

Authentication per connection

```
$ rsc tcp://localhost:8888 --sm user:password --smmt message/x.rsocket.authentication.basic.v0 -r hello -d World
Hello World, user!

# or

$ rsc tcp://localhost:8888 --sm user:password --smmt AUTHENTICATION_BASIC -r hello -d World
Hello World, user!
```