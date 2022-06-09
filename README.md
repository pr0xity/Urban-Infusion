<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/pr0xity/Urban-Infusion">
    <img src="src/main/resources/static/img/icons/logo-small.png" alt="Logo" height="80">
  </a>

<h3 align="center">Urban Infusion</h3>

  <p align="center">
    Group project for IDATA2301 Web Technologies and IDATA2306 Application development spring 2022 at NTNU Ålesund.
    <br />
    <a href="https://github.com/pr0xity/Urban-Infusion"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://gr03.appdev.cloudns.ph/">View Demo</a>
    ·
    <a href="https://github.com/pr0xity/Urban-Infusion/issues">Report Bug</a>
    ·
    <a href="https://github.com/pr0xity/Urban-Infusion/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#description">Description</a>
      <ul>
        <li><a href="#frameworks-and-tools">Frameworks and Tools</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#development">Development</a></li>
        <li><a href="#deployment">Deployment</a></li>
      </ul>
    </li>
    <li><a href="#functionality">Functionality</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## Description  

![Front Page Screen Shot](documentation/img/front-page.jpg)
Our project consists of developing a web store for a fictional herbal tea retail company called UrbanInfusion.

<p align="right"><a href="#top">back to top</a></p>

### Frameworks and Tools
This project was created using the following:

- [Spring boot](https://spring.io/)
- [Thymeleaf](https://www.thymeleaf.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [NginX](https://www.nginx.com/)
- [Docker](https://www.docker.com/)
- [Postman](https://www.postman.com/)

<p align="right"><a href="#top">back to top</a></p>

## Getting Started
### Prerequisites

- [Docker](https://www.docker.com/)
- [Java (17)](https://jdk.java.net/archive/)
- [Maven (4.0.0)](https://maven.apache.org/)

### Development

1. Start Postgres container in docker

   ```sh
   docker run -d --name PostgresDB -e POSTGRES_PASSWORD=YourPassword -p 6000:5432 postgres:14.2
   ```

2. Clone the repo

   ```sh
   git clone https://github.com/pr0xity/Urban-Infusion.git
   ```

3. Update application.properties

   - `spring.profiles.active` to match prod or dev
   - `spring.mail.username` to match email to use for mailing

4. Update application-dev.properties

   - `jwt.token.validity`
   - `jwt.signing.key`
   - `jwt.authorities.key`
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`

5. Package the java application

   ```sh
   mvn clean && mvn package
   ```

6. run the java application
   ```sh
   mvn spring-boot:run
   ```

### Deployment

1. Start Postgres container in docker

   ```sh
   docker run -d --name PostgresDB -e POSTGRES_PASSWORD=YourPassword -p 6000:5432 postgres:14.2
   ```

2. Clone the repo

   ```sh
   git clone https://github.com/pr0xity/Urban-Infusion.git
   ```

3. Update application.properties

   - `spring.profiles.active` to match prod or dev
   - `spring.mail.username` to match email to use for mailing

4. create application-prod.properties

   - `jwt.token.validity`
   - `jwt.signing.key`
   - `jwt.authorities.key`
   - `jwt.header.string`
   - `jwt.cookie.name`
   - `domain.name`
   - `domain.port`
   - `spring.mail.username`
   - `spring.mail.password`
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`  
      To make the application run on https use this guide
     https://web-tek.ninja/cookbook/https-spring-boot/  
     Remember to put the lines in application-prod.properties

5. Package the java application

   ```sh
   mvn clean && mvn package
   ```

6. run the java application
   ```sh
   nohup java -jar target/appdevapi-0.0.1-SNAPSHOT.war &
   ```

<p align="right"><a href="#top">back to top</a></p>

## Functionality

<table>
    <thead>
        <th>Customer</th>
        <th>Admin</th>
    </thead>
<tbody>
    <tr>
        <td>
            <h4>Account Management</h4>
            <ul>
                <li>create account</li>
                <li>edit/delete own account</li>
                <li>reset password</li>
            </ul>
        </td>
        <td>
            <h4>User Management</h4>
            <ul>
                <li>view all users</li>
                <li>
                    edit users'
                    <ul>
                        <li>name</li>
                        <li>email</li>
                        <li>address</li>
                    </ul>
                </li>
            </ul>
        </td>
    </tr>
    <tr>
        <td>
            <h4>Wishlist</h4>
            <ul>
                <li>add product to wishlist</li>
                <li>remove product from wishlist</li>
                <li>share wishlist with others</li>
            </ul>
        </td>
        <td>
            <h4>Order Management</h4>
            <ul>
                <li>view all orders</li>
                <li>mark/unmark orders as processed</li>
            </ul>
        </td>
    </tr>
    <tr>
        <td>
            <h4>Review</h4>
            <ul>
                <li>post a product review</li>
                <li>edit review made by this user</li>
                <li>remove review made by this user</li>
            </ul>
        </td>
        <td>
            <h4>Review Management</h4>
            <ul>
                <li>view all reviews</li>
                <li>edit display name of reviews</li>
                <li>delete reviews</li>
            </ul>
        </td>
    </tr>
    <tr>
        <td>
            <h4>Shopping</h4>
            <ul>
                <li>browse products</li>
                <li>add product to shopping cart</li>
                <li>remove product from shopping cart</li>
                <li>place a new order</li>
                <li>view purchase history</li>
            </ul>
        </td>
        <td>
            <h4>Product Management</h4>
            <ul>
                <li>view all products</li>
                <li>create new products</li>
                <li>edit existing products</li>
                <li>remove products from store</li>
            </ul>
        </td>
    </tr>
</tbody>
</table>

<p align="right"><a href="#top">back to top</a></p>

## License
Distributed under NTNU. For educational purposes only

<p align="right"><a href="#top">back to top</a></p>

## Contact
  
[Sakarias Sæterstøl](https://github.com/pr0xity) - sakariks@stud.ntnu.no  
[Janita Røyseth](https://github.com/janital) - janital@stud.ntnu.no  
[Didrik Eilertsen](https://github.com/didrikeilertsen) - didrikei@stud.ntnu.no  
[Nina Blindheim](https://github.com/ninablindheim) - ninavma@stud.ntnu.no  
[Espen Otlo](https://github.com/espenotlo) - espenotl@stud.ntnu.no

<p align="right"><a href="#top">back to top</a></p>

## Acknowledgments
Special thanks to the following libraries, tools and resources.

- [Google fonts](https://fonts.google.com/)
- [Phosphor icons](https://phosphoricons.com/)
- [BEM](http://getbem.com/)
- [Unsplash](https://unsplash.com/)
- [Pexels](https://www.pexels.com/)
- [Leaflet.js](https://leafletjs.com/)
- [Stadia Maps](https://stadiamaps.com/)
- [Openstreetmap](https://www.openstreetmap.org/)
- [Nominatim](https://nominatim.org/)

<p align="right"><a href="#top">back to top</a></p>
