## Stock-quotes service :chart_with_upwards_trend:

## endpoints
- /quotes Post and Get http methods.
- /quotes/{stock_id} Get method.
- /stockcache for internal use by the stock-manager API.

## simple POST request payload.
```json
{
  "stockId": "petr4",
  "quotes": {
    "2020-10-10": "22.20"
  }
}
```

### POST and GET response(GET all records will return an array of this object).
```json
{
  "id": "e244aeb6-699b-4ead-a498-634941e3bf8e",
  "stockId": "petr4",
  "quotes": {
    "2020-10-10": "22.20"
  }
}
```

## How to run quickly.
* Just download the docker-compose-yml
* install docker & docker-compose
* ```docker-compose up```
* You're good to go :rocket:

## How to run not so quickly.

* ```docker container run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -p 3306:3306 -p 33060:33060 -d mysql:8```
* ```docker container run -p 8080:8080 -d lucasvilela/stock-manager```
* Execute this project

![Alt Text](https://media3.giphy.com/media/3o7abB06u9bNzA8lu8/200.gif)