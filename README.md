#  Midterm Java project in Ironhack Openbank Opencamp

It's an individual project where I applied the knowledge of Java and MySQL gained during the first month of Ironhack Bootcamp.



## Description

The project consists backend code of a banking system. There are three type of users which ones are authenticated and authorized Bearer Auth.

There are different type of bank accounts such as student checking, checking, savings and credit cards.

Depending on the account's role and the bank account type there are different methods available, such as:
- transfering money in between accounts and to the third party users
- updating balance depending on monthly and annual interest rate
- creating new accounts (student checking, checking, savings and credit cards)
     


## Technologies used

Java, Spring Boot framework and MySQL database.



## Models

Controller, filter, model, repository, security, service

## Server routes table

| Method  | Route  | Description |
| :------------ |:---------------| :-----|
| GET     | /api/admins/balances | Display balances from all accounts (by authenticated admin) |
| GET      | /api/accountholders/balances       | Display balances corespondig to the logged in account holder (by authenticated accountHolder)  |
| POST | /api/admins/saveaccount    | Save account: student checking or checking depending on account holder age (by authenticated accountHolder) |
| POST | /api/admins/savecreditcard  | Save credit card (by authenticated admin)|
| POST | /api/admins/savesavings        | Save savings account (by authenticated admin) |
| POST | /api/admins/savethirdpartyuser | Save Third Party User (by authenticated admin) |
| PATCH |/api/savings/{id} | Update savings balance annualy depending on interest rate (by authenticated admin and account holder) |
| PATCH | /api/creditcards/{id} | Update credit card balance monthly depending on interest rate (by authenticated admin and account holder) |
| PATCH | /api/accountholders/transfer       | Update balances after the transfer between checking accounts is made (by authenticated account holder) |
| PATCH | /api/accountholders/transfer/thirdparty | Update balance after the transfer from checking to third party user is made (by authenticated account holder) |
| PATCH | /api/thirdpartyusers/transfer    | Update balance after the transfer from third party user to checking is made (by third party using secret key and hashkey as identification) |
| DELETE | /api/admins/deletethirdparty/{id} | Delete a third party user (by authenticated admin) |

There are already pre created admins, account holders and third party users as well as student checking, checking, saving accounts and credit cards so you can try them out using Postman. Here you have some templates ready for trying with the mentioned endpoints:
- for generating Admin access Token: http://localhost:8080/api/login?username=Laura&password=1234
- for generating AccountHolder access Token: http://localhost:8080/api/login?username=Edita&password=1234
- http://localhost:8080/api/admins/saveaccount - don't forget that this method is allowed only to Admins so a Token should be inserted and then this JSON format body: passed:
```json
{
    "balance": {
        "currency": "USD",
        "amount": 100
    },
    "primaryOwner": "Edita",
    "secondaryOwner": "Jess",
    "accountHolderId": 2
}
```
- http://localhost:8080/api/admins/savecreditcard - don't forget that this method is allowed only to Admins so a Token should be inserted and then this JSON format body:
```json
{
    "balance": {
        "currency": "USD",
        "amount": 50000
    },
    "primaryOwner": "Edita",
    "secondaryOwner": "Criss",
    "accountHolderId": 2,
    "interestRate": 0.15,
    "creditLimit":{
        "currency": "USD",
        "amount": 10000
    } 
}
```
- http://localhost:8080/api/admins/savesavings - don't forget that this method is allowed only to Admins so a Token should be inserted and then this JSON format body:
```json
{
    "balance": {
        "currency": "USD",
        "amount": 100
    },
    "primaryOwner": "Edita",
    "secondaryOwner": "Lina",
    "accountHolderId": 2
}
```
- http://localhost:8080/api/admins/savethirdpartyuser - don't forget that this method is allowed only to Admins so a Token should be inserted and then this JSON format body:
```json
{
    "name": "Jhon"
}
```
- http://localhost:8080/api/savings/5 - don't forget that this method is allowed to Admins and AccountHolders so a Token should be inserted
- http://localhost:8080/api/creditcards/1 - don't forget that this method is allowed to Admins and AccountHolders so a Token should be inserted
- http://localhost:8080/api/admins/balances - don't forget that this method is allowed only to Admins so a Token should be inserted
- http://localhost:8080/api/accountholders/balances - don't forget that this method is allowed only to AccountHolders so a Token should be inserted
- http://localhost:8080/api/accountholders/transfer - don't forget that this method is allowed only to AccountHolders so a Token should be inserted
```json
{
    "accountIdFrom" : 1,
    "accountIdTo" : 3,
    "primaryOwner" : "Jim",
    "transfer" : {
        "currency" : "USD",
        "amount" : 200
    }
}
```
- http://localhost:8080/api/thirdpartyusers/transfer - don't forget that the hashKey should be passed in the HTTP header and JSON body. You should take account's secret key and third's party hashKey from database, because it's different every time you run the program so the endpoint runs successfuly 
```json
{
    "hashkey": "[B@32f2de5c",
    "checkingId": 1,
    "primaryOwner": "Jess",
    "secondaryOwner": null,
    "checkingSecretKey": "[B@7f33ad20",
    "transfer": {
        "currency": "USD",
        "amount": 1000
    }
}
```
- http://localhost:8080/api/accountholders/transfer/thirdparty - don't forget that this method is allowed only to AccountHolders so a Token should be inserted and the JSON body (keep in mind that the account Id should belong to the Account Holder which is calling the endpoint):
```json
{
    "hashkey": "abcd",
    "transfer": {
        "currency" : "USD",
        "amount": 5000
    },
    "accountIdFrom": 1

}
```
- http://localhost:8080/api/admins/deletethirdparty/1 - don't forget that this method is allowed only to Admins so a Token should be inserted

## Future Works

It would be interesting to work on fraud detection method and front part of API.

## Resources

[Data Hashing in SQL Server](https://techcommunity.microsoft.com/t5/sql-server-blog/data-hashing-in-sql-server/ba-p/383909#:~:text=A%20hash%20is%20a%20number,generate%20the%20same%20hash%20value.&text=SQL%20Server%20has%20a%20built,HashBytes%20to%20support%20data%20hashing)

[Inheritance Strategies with JPA and Hibernate](https://thorben-janssen.com/complete-guide-inheritance-strategies-jpa-hibernate/#Table_per_Class)

[Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html)
[HTTP Status Codes](https://www.restapitutorial.com/httpstatuscodes.html)
