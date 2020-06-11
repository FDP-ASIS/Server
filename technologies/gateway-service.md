---
description: 'port: 8765'
---

# Gateway Service/Zuul Proxy Server

Zuul Server is a gateway application that handles all the requests and does the dynamic routing of microservice applications. The Zuul Server is also known as Edge Server.

For Example, **/api/user** is mapped to the user service and /api/software is mapped to the software service and Zuul Server dynamically routes the requests to the respective backend application.

![](../.gitbook/assets/zuul-service.png)

