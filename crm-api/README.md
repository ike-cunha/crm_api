<h1 align="center">CRM-API Project</h1>
<p align="center">CRUD with OAuth.</p>

<p align="center">
 <a href="#goal">Goal</a> •
 <a href="#funcionalities">Funcionalities</a> •
 <a href="#requirements">Requirements</a> •
 <a href="#running">Running</a> •
 <a href="#using">Using</a> •
 <a href="#technologies">Technologies</a> •
 <a href="#author">Author</a>
</p>

<h4 align="center"> 
	🧪 Beta ⚗️
</h4>

### Goal

Create a REST API to manage customer data for a small shop

### Funcionalities
🛠️

- [x] Create, Read, Update and Delete Users 
- [x] Create, Read, Update and Delete Customers
- [x] OAuth Integrated
- [x] Manage Photos

### Requirements

Before starting, make sure Docker [Docker](https://www.docker.com) is installed on your PC.

### Running

```shell script
# Clone this repo
$ git clone <https://github.com/ike-cunha/crm_api>

#Access the project in the terminal
$ cd .../crm_api/crm-api

#Linux / Mac
./mvnw

#Windows
./mvmw.cmd
```

```bash
# (IN NEAR FUTURE) In your terminal window type
$ docker run --name crm-api -d henriquecunha/crm-api
```

### Running
- In the Request Tool of your choice, run the endpoints shown in [Swagger](http://localhost:8080/q/dev/).
- If you want some standard request data you can use [Postman Collections](https://github.com/ike-cunha/crm_api/tree/main/crm-api/src/main/postman)

###Technologies
- [Quarkus](https://quarkus.io/)
- [Mysql](https://www.mysql.com/)
- [Flyway](https://flywaydb.org/)
- [RestEasy](https://resteasy.dev/)
- [Keycloak](https://www.keycloak.org/)

###CI/CD
- After every Git PUSH, a Docker image is updated at DockerHub (Next step -- Docker Compose) 

### Author

 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/23556713?s=400&u=6464c4e6297b42a9761f0964bc3bc3dd18bda537&v=4" width="100px;" alt=""/>
 <sub><b>Henrique Cunha</b></sub>

[![Linkedin Badge](https://img.shields.io/badge/-Henrique-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/henriquecunha/)](hhttps://www.linkedin.com/in/henriquecunha/)
[![Gmail Badge](https://img.shields.io/badge/-henrique.eccunha@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:henrique.eccunha@gmail.com)](mailto:henrique.eccunha@gmail.com)