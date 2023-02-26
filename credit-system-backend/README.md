
## Run & Build

It runs  on PORT: 8080

## Note

For the project to run without errors, the following code block must be added(with requirement parameters) to the application.properties file, or you can add a comment line on line 49 and line 69 ==>(smsService.sendSms(sendSmsDto);) in the SaveCreditWithUserService class.
```
twilio.account.sid=twilio-account-sid
twilio.auth.token=twilio-auth-token
twilio.phone.number=twilio-phone-number

```


```
git clone https://github.com/izzetdogan/credit-system-final-case.git

cd credit-system-backend/

$ docker-compose up

$ mvn clean 
$ mvn kotlin:compile
$ mvn install 

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


