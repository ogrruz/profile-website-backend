# profile-website-backend

This is the backend for my personal portfolio website. This works in conjunction with the database and the [frontend](https://github.com/ogrruz/profile-website-frontend) to deploy a working site prototype.
Below you can find instructions on how to deploy it locally on your machine.

## Local deployment

### Spring and Maven
As this project is using the spring framework with Maven build automation you will also need to install them.
Having installed them both, open the project in your IDE, open the terminal and navigate to the **/backend/** directory and run `mvn install` or `mvn clean install`. 
This will install all the required dependencies for the backend.

### Database

You will need a database instance running on a port of your machine. For this particular implementation I am using postgreSQL.
Thus, you will need to install pSQL and create a database schema to run on one of the ports of your machine. **Note the name of the database, port, username and password when you create it**.

This information needs to be input into **application.properties** file within this codebase in order for the backend to be able to communicate to it. 
Further more, it is better to use a local .env file references instead of hard-coded values within applciation.properties 
Once created the database will run on that port by default every time your machine starts.

### Starting the backend

Having installed the dependencies and configured the database, run the project by running the DemoApplication file in `backend/demo/src/main/java/com/example/demo/DemoApplication.java`
