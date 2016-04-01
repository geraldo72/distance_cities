# distance_cities
Projeto para calculo de distancia entre duas cidades, baseado na latitude/longitude

#Distância:
Foi utilizado a formula de Vincenty para calculo da distancia entre duas cordenadas (latitude/longitude).
Ela foi escolhida pois é mais precisa por levar em conta que o formato da terra não é uma esfera, como a formula Grande-Circulo considera, e sim um elipsoide.

#Combinação:
O metodo de combinação utilizado foi a permutação simples, pois a distância entre a cidade A - B é a mesma que B - A.
Recuperei a lista de cidades cadastradas no banco de dados e para cada cidade eu percorro uma nova lista que contem apenas as cidades que ainda nao foram iteradas.

#Sistema:
O sistema disponibiliza dois webservices REST:
 cities - Servico com funçoes basicas para manutenção das cidades
 
 - GET /distance_cities/rest/cities
   Retorna uma lista com todas as cidades
 
 - POST /distance_cities/rest/cities
   Inclui a cidade enviada no formato json. Ex:
   {
        "name": "Barueri",
        "latitude": -23.505776,
        "longitude": -46.838008
    }
 
 - GET /distance_cities/rest/cities/1
   Retorna a cidade com id 1
 
 - PUT /distance_cities/rest/cities/1
   Atualiza a cidade com id 1 com os dados enviados via json
 
 - DELETE /distance_cities/rest/cities/1 
   Deleta cidade com id 1
 
 distance - Servico que disponibiliza consulta de distancia entre cidades
 
 - GET /distance_cities/rest/distance?returnType=[xml/json]&measureUnit=[km/mi]
   Retorna lista com a combinação entre todas as cidades cadastradas e a distancia entre cada par. 
   Dois parâmetros obrigatórios, case insensitive: 
   		returnType - formato da reposta - Valores validos: xml, json
   		measureUnit - unidade de medida utilizada para distância - Valores validos: km, mi
 
 - GET /distance_cities/rest/distance/1?returnType=[xml/json]&measureUnit=[km/mi]
   Retorna uma lista com a distancia entre a cidade de id 1 e as outras cidades cadastradas
   Dois parâmetros obrigatórios, case insensitive: 
   		returnType - formato da reposta - Valores validos: xml, json
   		measureUnit - unidade de medida utilizada para distância - Valores validos: km, mi
    
   		
 - GET /distance_cities/rest/distance/1/2?returnType=[xml/json]&measureUnit=[km/mi]
   Retorna a distancia entre a cidade de id 1 e id 2
   Dois parâmetros obrigatórios, case insensitive: 
   		returnType - formato da reposta - Valores validos: xml, json
   		measureUnit - unidade de medida utilizada para distância - Valores validos: km, mi
 
Duas paginas para testar os servicos REST:
	- /distance_cities/index.html - Pagina para testar distancia
	- /distance_cities/city.html - Pagina para testar cidade

#Scripts:

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

#Instalação:
 - Via Maven:
 	Baixar os arquivos disponibilizados no GitHub (https://github.com/geraldo72/distance_cities)
 	Configurar o arquivo 'connection.properties' (src/main/resources/) com os dados do MySQL utilizado.
 		Caso as propriedades não sejam encontradas, irá tentar se conectar em localhost:3306 com usuario e senha 'teste'
 	Configurar o arquivo pom.xml (Raiz do projeto):
 		- tomcat.dir - Diretorio onde está o servidor tomcat
 		- output.dir - Diretório onde o war será criado. Está pré configurado para criar direto na pasta /webapps do tomcat, mas pode ser alterada para criar em outro lugar desejado. 
 	Na pasta onde foi baixado o projeto, executar o comando 'mvn clean compile package'
 	Iniciar o servidor, caso não esteja iniciado.

 - Via WAR:
 	Baixar o arquivo 'distance_cities.war' disponibilizado via email ou no GitHub (https://github.com/geraldo72/distance_cities/disp)
 	Configurar o arquivo 'connection.properties' dentro do war (distance_cities.war/WEB-INF/classes) com os dados do MySQL utilizado.
		Caso as propriedades não sejam encontradas, irá tentar se conectar em localhost:3306 com usuario e senha 'teste'
	Salvar o arquivo corretamente dentro do war.
	Copiar o war para a pasta de deploy do tomcat (/webapps)
	Iniciar o servidor, caso não esteja iniciado.
 	