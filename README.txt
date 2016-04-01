# distance_cities
Project for distance calculation between two cities, using latitude/longitude.

Distance:
The formula to calculate the distance between two coordinates (latitude / longitude) is the Vincenty's formulae .
It was selected because it has a higher precision due to calculation was based on ellipsoid, which is the geometric shape of the earth and not an sphere which is used on Great-Circle formula.

Combination:
The combination of method used was simple permutation, because the distance between the city A - B is the same as B - A.
In the code, i get a list with all cities inserted in the database and for each city I make a new list containing only the cities that have not yet been iterated.

System:
The system provides two REST webservices:
 cities - Service for CRUD operations on City element
 
 - GET /distance_cities/rest/cities
   Returns a list with all cities inserted on database.
 
 - POST /distance_cities/rest/cities
   Includes the city sent as json. Ex:
   {
        "name": "Barueri",
        "latitude": -23.505776,
        "longitude": -46.838008
    }
 
 - GET /distance_cities/rest/cities/1
   Returns the city with id 1
 
 - PUT /distance_cities/rest/cities/1
   Update the city with id 1 with the data sent as json
 
 - DELETE /distance_cities/rest/cities/1 
   Delete city with id 1
 
 distance - Service for get distance between two cities
 
 - GET /distance_cities/rest/distance?returnType=[xml/json]&measureUnit=[km/mi]
   Returns a list with all combinations with all cities registered and the distance between two of each
   Two mandatory parameters, case insentive:
   		returnType - Type of response - Valid values: xml,json
   		measureUnit - Measure unit used for distance - Valid values: km/mi
 
 - GET /distance_cities/rest/distance/1?returnType=[xml/json]&measureUnit=[km/mi]
   Returns a list with distance between the city with id 1 and all other cities registered
   Two mandatory parameters, case insentive:
   		returnType - Type of response - Valid values: xml,json
   		measureUnit - Measure unit used for distance - Valid values: km/mi
    
   		
 - GET /distance_cities/rest/distance/1/2?returnType=[xml/json]&measureUnit=[km/mi]
   Returns the distance between city with id 1 and city with id 2
   Two mandatory parameters, case insentive:
   		returnType - Type of response - Valid values: xml,json
   		measureUnit - Measure unit used for distance - Valid values: km/mi
   		
Two webpages for testing REST services:
	- /distance_cities/index.html - Distance page
	- /distance_cities/city.html - City page

Scripts:

drop database java;
CREATE DATABASE java;

drop user teste;
flush privileges;
create user teste identified by 'teste';
GRANT DELETE,INSERT, SELECT, UPDATE  ON java.* TO teste;

CREATE TABLE java.CITY (
  'id' INT NOT NULL AUTO_INCREMENT COMMENT 'Id of the city',
  'name' VARCHAR(200) NOT NULL COMMENT 'Name of city',
  'latitude' DECIMAL(10,8) NOT NULL COMMENT 'Degree value for latitude (+90 to -90) of the city',
  'longitude' DECIMAL(11,8) NOT NULL COMMENT 'Degree value for longitude (+180 to -180) of the city',
  PRIMARY KEY ('id'));
INSERT INTO java.CITY
('name',
'latitude',
'longitude')
VALUES
("Barueri",-23.505776, -46.838008),
("São Paulo",-23.550420, -46.633930),
("Rio de Janeiro",-22.912419, -43.230210),
("Las Vegas",36.062925, -115.016261),
("Stockholm",59.329292, 18.068564);

Installation:
 - With Maven:
    Download all files on GitHub (https://github.com/geraldo72/distance_cities)
	Configure MySql connection properties file 'connection.properties' (src/main/resources/)
 		Default configuration properties, if system don't find the configuration file:
 			- host: localhost
 			- port: 3306
 			- user: teste
 			- password: teste
 	Configure pom.xml file (Project root folder):
 		- tomcat.dir - Tomcat server folder
 		- output.dir - WAR output folder. Default configuration is webapps folder of tomcat server
	On downloaded project folder, execute mvn command with goals 'clean compile package'
	Start server, if is stopped.

 - With WAR:
 	Download 'distance_cities.war' file, sent by email or located on GitHub (https://github.com/geraldo72/distance_cities/disp)
	Configure MySql connection properties  file 'connection.properties' inside war (distance_cities.war/WEB-INF/classes)
 	Configurar o arquivo 'connection.properties' dentro do war (distance_cities.war/WEB-INF/classes) com os dados do MySQL utilizado.
		Default configuration properties, if system don't find the configuration file:
 			- host: localhost
 			- port: 3306
 			- user: teste
 			- password: teste
 	Save file inside WAR.
	Copy WAR file to tomcat deploy folder (/webapps)
	Start server, if is stopped.


