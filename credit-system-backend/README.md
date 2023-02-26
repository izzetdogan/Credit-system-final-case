
## Run & Build

It runs  on PORT: 8080

```
git clone https://github.com/izzetdogan/credit-system-final-case.git

cd credit-system-backend/

$ docker-compose up

$ mvn clean 
$ mvn kotlin:compile

- for Jar file 
$ mvn package or $ mvn install 


```

## Features
<ul>
<li> Database : Postgresql </li>
<li> Sms Service : Twilio </li>
<li> Kotlin data classes</li>
<li> Test : JUnity Mockito</li>
</ul>

## API EndPoints

Open API Document : http://localhost:8080/swagger-ui/index.html</br>

Get All User : http://localhost:8080/api/v1/users </br>
Add User with Credit Result: http://localhost:8080/api/v1/app (POST) </br>
Update User with Credit Result: http://localhost:8080/api/v1/app (PUT) </br>
Search User By NationalId and BirthDate : http://localhost:8080/api/v1/users/search?natId=&date= </br>


