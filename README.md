# Ironhack Openbank Opencamp - Midterm project Java

It's an individual project where I applied the knowledge of Java and MySQL gained during the first month of Ironhack Bootcamp.



## Description

The project consists backend code of a banking system. There are three type of users which ones are authenticated and authentified Bearer Auth.

There are different type of bank accounts such as student checking, checking, savings and credit cards.

Depending on the account's role and the bank account type there are different methods available, such as:
- transfering money in between accounts and to the third party users
- updating balance depending on monthly and annual interest rate
- creating new accounts
     


## Technologies used

Java, Spring Boot framework and MySQL database.



## Models

Controller, filter, model, repository, security, service

## Server routes table

| Method  | Route  | Description |
| :------------ |:---------------| :-----|
| GET     | /api/admins/balances | Display balances from all accounts |
| GET      | /api/accountholders/balances       | Display balances corespondig to the logged in account holder |
| POST | /api/admins/saveaccount    | Save account (student checking or checking) |
| POST | /api/admins/savecreditcard  | Save credit card |
| POST | /api/admins/savesavings        | Save savings account |
| POST | /api/admins/savethirdpartyuser | Save Third Party User |
| PATCH |/api/savings/{id} | Update savings balance annualy depending on interest rate |
| PATCH | /api/creditcards/{id} | Update credit card balance monthly depending on interest rate |
| PATCH | /api/accountholders/transfer       | Update balances after the transfer between checking accounts is made |
| PATCH | /api/accountholders/transfer/thirdparty | Update balance after the transfer from checking to third party user is made |
| PATCH | /api/thirdpartyusers/transfer    | Update balance after the transfer from third party user to checking is made |
| DELETE | /api/admins/deletethirdparty/{id} | Delete a third party user |

## Future Works

It would be interesting to work on fraud detection method and front part of API.

## Resources

[Data Hashing in SQL Server](https://techcommunity.microsoft.com/t5/sql-server-blog/data-hashing-in-sql-server/ba-p/383909#:~:text=A%20hash%20is%20a%20number,generate%20the%20same%20hash%20value.&text=SQL%20Server%20has%20a%20built,HashBytes%20to%20support%20data%20hashing)

[Inheritance Strategies with JPA and Hibernate](https://thorben-janssen.com/complete-guide-inheritance-strategies-jpa-hibernate/#Table_per_Class)

[Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html)