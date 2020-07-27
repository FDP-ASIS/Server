**Automatic Software Installation System** is a standard academic final degree project in software engineering. 
It was created for "Afeka College (Tel Aviv Academic College of Engineering)" which makes the software installation process in the various 
courses a fully automated, non-contact process.
This documentation is only for the server-side of the project.

**The project uses Spring boot** (open source Java-based framework) create a micro services.
There is 6 mocroservices:
**1. Discovery-service:**
   An application that holds information about all client-service applications. 
   Every Microservice will register into the Eureka server and the Eureka server knows all the client applications running 
   on each port and IP address. It deals with load balancing and can store a service when it has 2 instances up while working on two different ports. <br />
**2. Gateway-service:**
   Handles all the requests and does the dynamic routing of microservice applications. 
   The server knows the routes instead of addressing them individually. The Zuul Server is also known as Edge Server. <br />
**3. Auth-service:**
   * The authentication flow is simple:
   * The user sends a request to get a token bypassing his credentials.
   * The server validates the credentials and sends back a token. With every request, the user has to provide the token, and the server will validate that token. <br />
**4. User-service:**
   All operations that admin/lecturer/student can use and data about the user in the system. <br />
**5. Course-service:**
   All operations that admin/lecturer/user can use. 
   This service stores data about the courses in the system. <br />
**6. Software-servie:**
   Software data saved in an external repository: https://github.com/FDP-ASIS/Repository/tree/master/scripts
   The user can get the software name and the versions. Also, he can get the script file for downloading the software to his personal computer. <br />

**The database in the system is MongoDB** (cross-platform document-oriented database program. 
Classified as a NoSQL database program, MongoDB uses JSON-like documents with optional schemas)
