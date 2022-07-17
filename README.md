# NinjaOne Backend Interview Project - Solution

Solution to [NinjaOne Assessment](https://github.com/NinjaRMM/backend-interview-project-app-template). The project will create a database schema with all the requirements and following the best practices. It al so has Spring Security with JWT and Unit Tests.

## Starting the Application

1. Get Gradle dependencies
2. Run the BackendInterviewProjectApplication class, setting the active profile to development

## API Documentation

You can import the requests collection to postman with this [JSON Link](https://www.getpostman.com/collections/b5c8173d4f1c1fcc9636)

All the requests must include a valid bearer token. On the startup two users are created:
* Admin:
  - Username: admin@ninjaone.com
  - Password: admin

* Customer:
  - Username: customer@ninjaone.com
  - Password: customer
  
You can get the bearer token with a post request to: localhost:8080/auth/login
with the body:
```
{
    "email":"admin@ninjaone.com",
    "password":"admin"
}
```
Customers can only handle their own resources. If they try to access another resource they will receive an exception.
Admins can handle all the resources
You can make request to handle:
* [Devices](#devices)
* [Services](#services)
* [Customers](#customers)

### Devices
Available methods:
* [Get Devices](#get-devices)
* [Get Device](#get-device)
* [Create Device](#create-device)
* [Update Device](#update-device)

### Get Devices

Lists all the devices of the given customer

Endpoint: localhost:8080/v1.0/customers/CUSTOMER_ID/devices
HTTP Method: GET

### Get Device

Lists the device details with the given IDs

Endpoint: localhost:8080/v1.0/customers/CUSTOMER_ID/devices/DEVICE_ID
HTTP Method: GET

### Create Device

Creates a new device to the given customer 

Endpoint: localhost:8080/v1.0/customers/CUSTOMER_ID/devices
HTTP Method: POST

JSON Body Example:
```
{
    "systemName": "Windows",
    "type": {
        "id": "4028c4fc81ef254c0181ef2554630000",
        "detail": "Windows Workstation"
    },
    "services": [
        {
            "id": "4028c4fc81ef49470181ef494fcf0000",
            "description": "Device"
        }
    ]
}
```

### Update Device

Updates a device of the given customer

Endpoint: localhost:8080/v1.0/customers/CUSTOMER_ID/devices/DEVICE_ID
HTTP Method: PUT

JSON Body Example:
```
{
    "systemName": "Windows",
    "type": {
        "id": "4028c4fc81ef254c0181ef2554630000",
        "detail": "Windows Workstation"
    },
    "services": [
        {
            "id": "4028c4fc81ef49470181ef494fdf0001",
            "description": "Antivirus"
        }
    ]
}
```

### Services
Available methods:
* [Get Services](#get-services)
* [Get Service](#get-service)
* [Create Service](#create-service)
* [Update Service](#update-service)

### Get Services

Lists all the services

Endpoint: localhost:8080/v1.0/services
HTTP Method: GET

### Get Service

Lists the service details with the given ID

Endpoint: localhost:8080/v1.0/services/SERVICE_ID
HTTP Method: GET

### Create Service

Creates a new service

Endpoint: localhost:8080/v1.0/services
HTTP Method: POST

JSON Body Example:
```
{
    "description": "Test",
    "costs": [
        {
            "value": 5.0,
            "currency": "USD",
            "deviceType": {
                "id": "4028c4fc81ef254c0181ef2554630000",
                "detail": "Windows Workstation"
            }
        },
        {
            "value": 7.0,
            "currency": "USD",
            "deviceType": {
                "id": "4028c4fc81ef254c0181ef25546c0002",
                "detail": "Mac"
            }
        },
        {
            "value": 5.0,
            "currency": "USD",
            "deviceType": {
                "id": "4028c4fc81ef254c0181ef25546c0001",
                "detail": "Windows Server"
            }
        }
    ]
}
```

### Update Service

Updates a service

Endpoint: localhost:8080/v1.0/services/SERVICE_ID
HTTP Method: PUT

JSON Body Example:
```
{
    "description": "Antivirus",
    "costs": [
        {
            "value": 4.0,
            "currency": "USD",
            "deviceType": {
                "id": "4028c4fc81ef28580181ef2860500000",
                "detail": "All"
            }
        }
    ]
}
```

### Customers
Available methods:
* [Get Customers](#get-customers)
* [Get Customer](#get-customer)
* [Create Customer](#create-customer)
* [Update Customer](#update-customer)

### Get Customers

Lists all the customers

Endpoint: localhost:8080/v1.0/customers
HTTP Method: GET

### Get Customer

Lists the customer information with the given ID

Endpoint: localhost:8080/v1.0/customers/CUSTOMER_ID
HTTP Method: GET

### Create Customer

Creates a new customer

Endpoint: localhost:8080/v1.0/customers
HTTP Method: POST

JSON Body Example:
```
{
    "email": "testcreate@ninjaone.com",
    "password": "test",
    "roles": [{"id":"ff80808181ef0f7c0181ef0f841b0001","name":"CUSTOMER"}],
    "devices": []
}
```

### Update Customer

Updates a customer with the given ID

Endpoint: localhost:8080/v1.0/customers/CUSTOMER_ID
HTTP Method: PUT

JSON Body Example:
```
{
    "email": "updatedEmail@newemail.com",
    "password": "updatedcustomer",
    "roles": [{"id":"ff80808181ef0f7c0181ef0f841b0001","name":"CUSTOMER"}],
    "devices": []
}
```

## H2 Console 

In order to see and interact with your db, access the h2 console in your browser.
After running the application, go to:

http://localhost:8080/h2-console

Enter the information for the url, username, and password in the application.yml:
```
url: jdbc:h2:mem:localdb
username: sa 
password: password
```
