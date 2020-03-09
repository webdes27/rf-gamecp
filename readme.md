# About

The project was created with the purpose of being open source. It consists of backend and frontend integrated in the
same project. Feel free to do your job with the stylesheets and images.

# How to use

* Edit the application.properties file in src / main / resources,
 putting your access credentials and URLs to your RF databases.
* Create a new database called GameCP on your sql server, then run the SQL scripts that are in the SQL_GAMECP
 file in src / main / resources.
* Edit the environment variables in src / main / resources / frontend / rfgcp / src / environments, changing the IPs,
 for your server. 
* Open the terminal and in the project's root folder, run the mvn package command, or,
if you're using something like an IDE, simply build the project,
and run the .jar file generated in the "root / target" folder.

### Intentions

The intention of the project is to encourage the constant search for security within RF Online, I will try to be 
updating it with the best practices as far as possible for me. Any suggestions make an issue, any ideas?
Make a pull request.
