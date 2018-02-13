## **SERVER INSTALLATION**
Requirements - you need java 8 to run compiled jar files.

How to run: execute following commands

'java -jar -Dspring.profiles.active=master server-1.0-SNAPSHOT.jar'

'java -jar -Dspring.profiles.active=slave1 server-1.0-SNAPSHOT.jar'

'java -jar -Dspring.profiles.active=slave2 server-1.0-SNAPSHOT.jar'

to startup an master node and two slave nodes or run corresponding main classes from IDE of choice with different active spring profiles 'master', 'slave1', 'slave2'.

Ports required to be available - 32232-32233 & 8080 (change in application.yaml if needed).

## **API**
PUT ${host}:8080/searchApi/documents - index a document

example body:

{"key":"key1","document":"Hello World"}

POST ${host}:8080/searchApi/search - search documents by term

example body:

{"tokens":["Hello","world"]}

GET ${host}:8080/searchApi/documents/${documentKey} - get document by key

## **RUNNING CLIENT**
Requirements - you need java 8 to run compiled jar files.

How to run:

execute following command 'java -jar client-1.0-SNAPSHOT.jar' to startup an client or run corresponding main class from IDE of choice.

It will run a short system test that will put 100 document with key equal to it's counter and content like 'mode5is1 mode3is1 1'.

Where first token is counter modulo 5, second is counter modulo 3 and last one is counter itself. Expected output is

{"document":"mod5is2 mod3is0 42"}

{"document":"mod5is3 mod3is0 78"}

{"document":"mod5is4 mod3is2 29"}

{"keys":["0","45","15","90","60","30","75"]}

{"keys":["42"]}

{"keys":["55","45","35","25","15","0","5","90","80","70","60","50","95","40","30","85","75","20","65","10"]}

{"keys":["30"]}

{"keys":["22","67","37","7","82","52","97"]}

Order of keys could be different because of random partitioning