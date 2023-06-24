# Network Requests with CURL samples
> For windows use `cmd` command

## Requests for admin user
### get all users
`curl -X GET http://localhost:8080/topjava/rest/admin/users`

### create new user
`curl -X POST -H "Content-Type: application/json" -d "{\"name\": \"New2\",\"email\": \"new21@yandex.ru\",\"password\": \"passwordNew\", \"roles\": [\"USER\"]}" http://localhost:8080/topjava/rest/admin/users`

### get user by id (for example id = 100001)
`curl -X GET http://localhost:8080/topjava/rest/admin/users/100001`

### update existed user
`curl -X PUT -H "Content-Type: application/json" -d "{\"name\": \"UserUpdated\",\"email\": \"user@yandex.ru\",\"password\": \"passwordNew\",\"roles\": [\"USER\"]}" http://localhost:8080/topjava/rest/admin/users/100000`

### get user by Email
`curl -X GET http://localhost:8080/topjava/rest/admin/users/by-email?email=guest@gmail.com`

### Requests for admin user, AdminWithMeals
`curl -X GET http://localhost:8080/topjava/rest/admin/users/100000/with-meals`

## Requests for profile user
### get user
`curl -X GET http://localhost:8080/topjava/rest/profile`

### update user
`curl -X PUT -H "Content-Type: application/json" -d "{\"name\": \"New777\",\"email\": \"new777@yandex.ru\",\"password\": \"passwordNew\",\"roles\": [\"USER\"]}" http://localhost:8080/topjava/rest/profile`

### delete user
`curl -X DELETE http://localhost:8080/topjava/rest/profile`

## get user with his meals
`curl -X GET http://localhost:8080/topjava/rest/profile/with-meals`

## Requests for meals
### create new meal for profile user
`curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\": \"2020-02-01T10:01\",\"description\": \"Новый завтрак\",\"calories\": 777}" http://localhost:8080/topjava/rest/meals`

### get all meals for all user
`curl -X GET http://localhost:8080/topjava/rest/meals`

### get all meals for user by id (for example id = 100003)
`curl -X GET http://localhost:8080/topjava/rest/meals/100003`

### update existed meal for user by id (for example id = 100003)
`curl -X PUT -H "Content-Type: application/json" -d "{\"name\": \"New777\",\"email\": \"new777@yandex.ru\",\"password\": \"passwordNew\",\"roles\": [\"USER\"]}" http://localhost:8080/topjava/rest/meals/100003`

### delete meal for user by id (for example id = 100003)
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100003`

### filtered meal with 4 parameters : 
- start Date (format date `YYYY-MM-DD`)
- start Time (format time `HH:mm`)
- end Date (format date `YYYY-MM-DD`)
- end Time (format date `HH:mm`)

`curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-30&endTime=15:00"`

each parameter may be nullable
`curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=&endTime="`
