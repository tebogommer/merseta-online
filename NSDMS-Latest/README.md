# Getting Started
TODO: Guide users through getting your code up and running on their own system.
1.	Insure you have the followig installed (if a version is referanced below this section was last updated 2020-04-06)
-   eclipse
-   jboss tools plugin in eclipse
-   Mysyql server
-   the appropriate mysyql jdbc driver
-   java 8 
-   tomcat 8.7 and add this as a new server to your eclipse 
-   restore a backup of the database

2. configure datasource in server.xml with the following and changing the server details

        <Resource acquireIncrement="1" auth="Container" description="MERSETA" driverClass="com.mysql.jdbc.Driver" factory="org.apache.naming.factory.BeanFactory" idleConnectionTestPeriod="600" initialPoolSize="1" jdbcUrl="jdbc:mysql://127.0.0.1:3306/merseta?zeroDateTimeBehavior=convertToNull" maxIdleTime="600" maxPoolSize="2" maxStatements="0" minPoolSize="1" name="/jdbc mersetadatasource" password="{password}" preferredTestQuery="select 1;" type="com.mchange.v2.c3p0.ComboPooledDataSource" user="{username}" />

3. configure datasource in context.xml with the following and changing the server details

        <ResourceLink global="/jdbc/mersetadatasource" name="/jdbc/mersetadatasource" type="javax.sql.DataSource"/>

4. Latest releases
    Check out the appropriate branch that you will be working with.
    Regular sync (push and pull of the code) will ensure you have the latest version.


5. Naming Conventions

    All Database tables/fields are snake case. ie first_name, company_users

    Database tables have a one-one mapping to their relavant java class. For Example:
        
        company_users will map to the java class CompanyUsers.java
    
    Column names will map one to one to the relavant field within their java class. For Example:
        
        first_name on the users table will map to a field on the Users.java called firstName


6. Code Structure

    All classes follow the below pattern, an entity will have its own DAO layer, Service layer and UI Layer. For example 
    
    **Entity** *these map to the database tables.*
        
    - CompanyUsers.java 
        
    **DAO** *These allow all CRUD operations*
    
    - CompanyUsersDao.java
    
    **Service** *this is where the business logic will sit*
    
    - CompanyUsersService.java
    
    **UI** *this is the layour for representing the data on screen*
    - CompanyUserUI.java

7. Maven

    A new maven repository will need to be set up in order to access specific jar files that are not on maven public repository. 
    Also to note: **until the dependencies are resolved the pom.xml will be in a error state**.

    The three jar files are located in the **Notes** folder on the project.

    The three jar files are as follows:
    - barcode4j-light-2.1.jar
    - primefaces-6.2.24.jar
    - primefaces-extensions-6.2.8.jar

    On the production web server and test enviroment the jars mentioned above are in the **.M2** folder.

    The previous reporsitory used was JFrog artifactory.
    
    URL to JFrog Artifactory: 

        https://jfrog.com/artifactory/

    If you would like to use JFrog Artifactory, below is the url to help with configuration & set up:

        https://computingforgeeks.com/how-to-install-jfrog-artifactory-on-ubuntu-linux/

    Once a repository has been configured and deployed, the pom.xml will need to be updated with the URL for access. 

    Where updates need to take place in the pom.xml can be identified by the tag:
    
        #ADD_URL_HERE#
    
    However this is a maven project, you can use alternative methods to resolve the dependencies for the build and runtime. 


    




