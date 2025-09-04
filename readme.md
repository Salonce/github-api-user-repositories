# GitHub Repositories API 🔍

A Spring Boot application that exposes a REST endpoint to fetch data about user repositories.

## ✨ Features

- Fetches information about public repositories of a user.
- Filters out forked repositories.
- For each user's github repository lists:
  the name, owner login and data for each branch: its name and the latest SHA commit.

---

## 🔗 API Endpoint

```http
GET /users/{username}/repositories
```

## 🧠 Example usage

### Request:

```http
GET /users/octocat/repositories
```


### Response:
```json
[
    {
        "name": "git-consortium",
        "owner_login": "octocat",
        "branches": [
            {
                "name": "master",
                "sha": "b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e"
            }
        ]
    },
    {
        "name": "hello-worId",
        "owner_login": "octocat",
        "branches": [
            {
              "name": "master",
              "sha": "7e068727fdb347b685b658d2981f8c85f7bf0585"
            }
        ]
    },
    {
        "name": "Hello-World",
        "owner_login": "octocat",
        "branches": [
            {
             "name": "master",
              "sha": "7fd1a60b01f91b314f59955a4e4d4e80d8edf11d"
            },
            {
                "name": "octocat-patch-1",
                "sha": "b1b3f9723831141a31a1a7252a213e216ea76e56"
            },
            {
                "name": "test",
                "sha": "b3cbd5bbd7e81436d2eee04537ea2b4c0cad4cdf"
            }
        ]
    },
    // and so forth...
]
```

## 🧯 Error Responses

#### User not found - example request:
```
GET /users/nonexistantuser/repositories
```
#### Response:
```json
{
"status": 404,
"message": "User 'nonexistantuser' not found."
}
```

#### Other errors response:

```json
{
  "status": 500,
  "message": "Unexpected error occurred"
}
```

### ⚙️ Technologies Used
- Java 21
- Spring Boot 3.5
- Spring Web
- Jackson
- GitHub REST API v3

### 🚀 Prerequisites for running the application

- Java 21
- Maven 3.9.3 or later
- Internet access

### Run via Maven:
```
./mvnw spring-boot:run
```
