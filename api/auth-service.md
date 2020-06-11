---
description: 'User Authentication- port: 8080'
---

# Auth Service

**Authentication Workflow**  
The authentication flow is simple as:

1. The user sends a request to get a token passing his credentials.
2. The server validates the credentials and sends back a token.
3. With every request, the user has to provide the token, and server will validate that token.

\*\*\*\*

![](../.gitbook/assets/auth-service.jpg)

{% api-method method="post" host="/auth/login" path="/" %}
{% api-method-summary %}
Login User
{% endapi-method-summary %}

{% api-method-description %}
Log user in to the system with username and password, give him a new token after authentication
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-body-parameters %}
{% api-method-parameter name="username" type="string" required=true %}

{% endapi-method-parameter %}

{% api-method-parameter name="password" type="string" required=true %}

{% endapi-method-parameter %}
{% endapi-method-body-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Successfully login users
{% endapi-method-response-example-description %}

```text
{
  "token": "string",
  "user": {
    "email": "string",
    "id": "string",
    "role": "ADMIN",
    "username": "string"
  }
}
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=401 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
Invalid credential
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
Other internal errors
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="delete" host="/auth/logout" path="/" %}
{% api-method-summary %}
Logout user
{% endapi-method-summary %}

{% api-method-description %}
Logout from the system and delete the token
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=205 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
Successfully logout user
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=400 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
Bad request
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
Other internal errors
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

