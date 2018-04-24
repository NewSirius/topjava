####Get the meal by id.
curl --request GET http://localhost:8080/topjava/rest/meals/100003

response example:
{"id":100003,"dateTime":"2015-05-30T13:00:00","description":"Обед","calories":1000,"user":null}
####Get all the meals for the registered user.
curl --request GET http://localhost:8080/topjava/rest/meals

response example:
[{"id":100007,"dateTime":"2015-05-31T20:00:00","description":"Ужин","calories":510,"exceed":true},{"id":100006,"dateTime":"2015-05-31T13:00:00","description":"Обед","calories":1000,"exceed":true},{"id":100005,"dateTime":"2015-05-31T10:00:00","description":"Завтрак","calories":500,"exceed":true},{"id":100004,"dateTime":"2015-05-30T20:00:00","description":"Ужин","calories":500,"exceed":false},{"id":100003,"dateTime":"2015-05-30T13:00:00","description":"Обед","calories":1000,"exceed":false},{"id":100002,"dateTime":"2015-05-30T10:00:00","description":"Завтрак","calories":500,"exceed":false}]
####Delete meal by id
curl --request DELETE http://localhost:8080/topjava/rest/meals/100003

####Save given JSON meal. 
curl -H "Content-Type: application/json" -d "{"dateTime":"2018-04-21T19:00:00","description":"Ужин","calories":800,"user":null}" http://localhost:8080/topjava/rest/meals

######return:

{"id":100015,"dateTime":"2018-04-21T19:00:00","description":" Ужин","calories":800,"user":null}

#### Update meal by ID.
curl -H "Content-Type: application/json" --request PUT -d '{ "id": 100015, "dateTime": "2018-04-24T20:00:00", "description": "Description", "calories": 777, "user": null }' http://localhost:8080/topjava/rest/meals/100015

####Get all meals with filter by Date/Time
curl -d "startDate=2015-01-03&startTime=10:01:00&endDate=2015-12-04&endTime=21:15:00" http://localhost:8080/topjava/rest/meals/filter

######return:
[{"id":100007,"dateTime":"2015-05-31T20:00:00","description":"Ужин","calories":510,"exceed":true},{"id":100006,"dateTime":"2015-05-31T13:00:00","description":"Обед","calories":1000,"exceed":true},{"id":100004,"dateTime":"2015-05-30T20:00:00","description":"Ужин","calories":500,"exceed":false}]
