# Interview environment

The purpose of this repo is to provide the following information
* Provide example of documentation expectations
* Answer complex interview questions in real time
* Demonstrate knowledge in a variety of technologies

## Requirements
Docker compose needs to be installed on your machine

Confluent hub should also be installed.

see https://docs.confluent.io/platform/current/connect/confluent-hub/client.html

after confluent hub has been installed instal kafka connect for jdbc
```
confluent-hub install confluentinc/kafka-connect-jdbc:10.7.3 --/component-dir ~/connect 
```

## MYSQL

To connect to the database run the following
```
docker exec -it mysql mysql --user=root --password=rootIsABadPassword
```

some helpful commands
```
show databases;
```


## Kafka

Creating a topic

