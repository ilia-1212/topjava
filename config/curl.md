# curl samples

## requests for admin user
### AdminGetAll
curl -X GET http://localhost:8080/topjava/rest/admin/users

### AdminCreate
curl -X POST -H "Content-Type: application/json" -d "{\"name\": \"New2\",\"email\": \"new21@yandex.ru\",\"password\": \"passwordNew\", \"roles\": [\"USER\"]}" http://localhost:8080/topjava/rest/admin/users/

### AdminGet
curl -X GET http://localhost:8080/topjava/rest/admin/users/100001

### AdminUpdate
curl -X PUT -H "Content-Type: application/json" -d "{\"name\": \"UserUpdated\",\"email\": \"user@yandex.ru\",\"password\": \"passwordNew\",\"roles\": [\"USER\"]}" http://localhost:8080/topjava/rest/admin/users/100000

## requests for profile user
### ProfileGet
curl -X GET http://localhost:8080/topjava/rest/profile

### ProfileUpdate
curl -X PUT -H "Content-Type: application/json" -d "{\"name\": \"New777\",\"email\": \"new777@yandex.ru\",\"password\": \"passwordNew\",\"roles\": [\"USER\"]}" http://localhost:8080/topjava/rest/profile

### ProfileDelete
curl -X DELETE http://localhost:8080/topjava/rest/profile

## requests for user meals
### Create
curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\": \"2020-02-01T10:01\",\"description\": \"Новый завтрак\",\"calories\": 777}" http://localhost:8080/topjava/rest/meals

### GetAll
curl -X GET http://localhost:8080/topjava/rest/meals

### Get
curl -X GET http://localhost:8080/topjava/rest/meals/100003

### Update
curl -X PUT -H "Content-Type: application/json" -d "{\"name\": \"New777\",\"email\": \"new777@yandex.ru\",\"password\": \"passwordNew\",\"roles\": [\"USER\"]}" http://localhost:8080/topjava/rest/meals/100003

### Delete
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

### filter
curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-30&endTime=15:00"


### requests for admin user, AdminGetByEmail
curl -X GET http://localhost:8080/topjava/rest/admin/users/by-email?email=guest@gmail.com

### requests for admin user, AdminWithMeals
curl -X GET http://localhost:8080/topjava/rest/admin/users/with-meals/100000

## requests for profile user, WithMeals
curl -X GET http://localhost:8080/topjava/rest/profile/with-meals