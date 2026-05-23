# Store API


## How to run

### Make sure you have all these things installed

1. `Docker` (https://docs.docker.com/get-started/)
2. `make` (Optional)

### Running the app

You could run this project via docker command

`docker compose up -d`

Or, if you have `make` installed you could run

**Available `make` commands:**
*   `make up` : Starts the app and database.
*   `make down` : Stops the services.
*   `make rebuild`: Rebuilds the code and restarts services.
*   `make clean` : Resets the entire environment (wipes database data).

After you have running one of those commands, the app will be avaailable on http://localhost:8080

This project also provides [`Scalar`](https://scalar.com/) web interface that you could access at http://localhost:8080/docs

We have initial data seeded, so you dont need to add it manually on the first time

## Environment Variables

Available environment variables:

* `DB_URL`: JDBC connection URL. Default: `jdbc:postgresql://localhost:5432/store`
* `DB_USERNAME`: Database username. Default: `admin`
* `DB_PASSWORD`: Database password. Default: `admin123`
* `DB_DDL_AUTO`: Hibernate DDL mode. Default: `validate`

## Assumptions Made
- Single tenant
- Single currency (USD)
- Single warehouse

## Design Decisions
- Atomic Transaction for Stock Handling
  - for stock operation, i did use atomic sql queries to prevent invalid db state
- Global Exception Handling
  - Added this for standardizing error and api response, ensuring the consumer receives consisten data shape. Also added `ServerException` and `ClientException` to differentiate between errors that are safe to send to client or not. `ServerException` message by default will be masked with "Something went wrong" kind of message 

## API Endpoint Examples

You could access this in scalar docs `https://localhost:8080/docs`

All JSON API responses are wrapped in this shape:
```json
{
  "message": "ok",
  "result": {}
}
```

### Health Check
`GET /ping`
```text
pong
```

### Get All Items
`GET /items`
```json
{
  "message": "ok",
  "result": [
    {
      "id": "4f8b4d7a-2c6a-4f65-bbf4-8bb874f2ab0c",
      "name": "Classic T-Shirt",
      "description": "Cotton crew neck t-shirt",
      "variants": [
        {
          "id": "88b40ab9-11ce-4e3f-90ec-6e31eb5ed607",
          "sku": "TSHIRT-BLK-M",
          "name": "Black / M",
          "price": 19.99,
          "stockOnHand": 10,
          "stockAllocated": 2,
          "availableStock": 8
        }
      ]
    }
  ]
}
```

### Create Item
`POST /items`
```json
{
  "name": "Classic T-Shirt",
  "description": "Cotton crew neck t-shirt"
}
```
**Success (201):**
```json
{
  "message": "ok",
  "result": {
    "id": "4f8b4d7a-2c6a-4f65-bbf4-8bb874f2ab0c",
    "name": "Classic T-Shirt",
    "description": "Cotton crew neck t-shirt",
    "variants": []
  }
}
```

### Delete Item
`DELETE /items/{id}`
* **Success (204):** Item is deleted.
* **Error (404 - `ITM-4004`):** Item not found.

### Add Variant
`POST /items/{itemId}/variants`
```json
{
  "sku": "TSHIRT-BLK-M",
  "name": "Black / M",
  "price": 19.99,
  "initialStock": 10
}
```
**Success (201):**
```json
{
  "message": "ok",
  "result": {
    "id": "88b40ab9-11ce-4e3f-90ec-6e31eb5ed607",
    "sku": "TSHIRT-BLK-M",
    "name": "Black / M",
    "price": 19.99,
    "stockOnHand": 10,
    "stockAllocated": 0,
    "availableStock": 10
  }
}
```
* **Error (404 - `ITM-4004`):** Item not found.

### Restock Variant
`POST /variants/{id}/stock/restock`
```json
{ "quantity": 5 }
```
**Success (200):**
```json
{
  "message": "ok",
  "result": null
}
```
* **Error (404 - `INV-4004`):** Variant not found.

### Reserve Stock
`POST /variants/{id}/stock/reserve`
```json
{ "quantity": 2 }
```
**Success (200):**
```json
{
  "message": "ok",
  "result": null
}
```
* **Error (400 - `INV-4000`):** Insufficient stock available.
* **Error (404 - `INV-4004`):** Variant not found.

### Remove Stock (Shrinkage)
`POST /variants/{id}/stock/remove`
```json
{ "quantity": 1 }
```
**Success (200):**
```json
{
  "message": "ok",
  "result": null
}
```
* **Error (400 - `INV-4000`):** Insufficient stock available.
* **Error (404 - `INV-4004`):** Variant not found.

### Error Response
```json
{
  "message": "error",
  "result": {
    "code": "INV-4000",
    "message": "Insufficient stock available"
  }
}
```