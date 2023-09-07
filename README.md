# order-service-backend
# 1. About
The project involves the creation of an order management system that consists of both user and staff components. 
Users can create orders, which are instantly sent to the staff for processing. The backend of the system was built using technologies such as Java, the Spring Framework, and Hibernate. 
Testing was performed using JUnit and Mockito. To ensure authentication and authorization, Spring Security with JWT tokens was employed. MySQL serves as the database technology. 
For the frontend, ReactJS, HTML, and CSS were used. 
Communication between the server and client sides of the project occurs through HTTP and WebSocket protocols.

Front-end - https://github.com/BranetE/Identa-task-frontend
# 2. Instructions on how to run
1. Clone this repository and set up the frontend part of the project using the provided link. 
2. Open the project in an IDE and configure the following environment variables. 
DATABASE_PASSWORD=root; 
DATABASE_URL=jdbc:mysql://localhost:3306/orderservice; 
DATABASE_USERNAME=root
3. Create a database named "orderservice" in MySQL and run the scripts from the "create.sql" file located in the resources folder.
4. Finally, launch the project.
5. The project starts on localhost:8080 you can test endpoints via postman https://www.postman.com/aerospace-technologist-12704173/workspace/identa-task/overview
