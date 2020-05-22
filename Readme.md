 # **Prueba Tecnica S4N - Domicilios con drones**
 #### Author: Daniel Vargas - davaor@gmail.com
 Command line application to read, validate, process and display bowling game data 
 
 ## Video explicativo de diseño  (3 Minutos)
  https://drive.google.com/open?id=1LqZNigsfCutcBqtif0lpBuKHtT5mpTgu
 
 #### Stack de tecnologia:
     - Java
     - Maven
 
 #### Requerimientos:

     - Java 8 
     - Maven 
     - Terminal
 
 #### Correr Aplicacion:
 Estando en la raiz del proyecto "drondelivery"
        
      Ejecutar comando maven:
      - mvn exec:java
      
      Alternativamente la aplicacion se puede correr en un IDE Java

 #### Correr las pruebas
     
     - mvn test
     
 #### Parametrizacion de la applicacion
    La applicacion se puede parametrizar cambiando las propeiedades en el archivo:
    -src/resources/app.properties
    De esta manera se pueden modificar las reglas de negocio basicas
         
 ### Modelo de Dominio
    Direction: Puntos cardinales
    Position: La posicion en el mapa esta definida por coordenadas x, y y orientacion
    Dron: Representado por ubicacion actual, rutas asignadas y entregas.
    
    
 ### Diseño
    Applicacion orientada objetos, usando java 8, sin uso  de frameworks
    Diseño riguroso usando criterios SOLID
    Inversion de dependencias
    Thread pool para ejecucion paralela de los drones disponibles
    Factory para creacion de drones 
    Facade para agregacion de clases con logica de negocio
    
  ### Concurrencia
    Para garantizar que los drones puedan operar en simultaneo, cada vuelo de dron se ejecuta en un hilo
    Para evitar que los drones se estrellen entre ellos, cada dron hace un lock de las coordenadas destino, 
    en cada paso de la ruta
    
   ### Resilencia
    Cada dron es una entidad independiente que trata de ejecutar su ruta o parte de ella de acuerdo con su parametrizacion
    La logica de negocio tiene manejo extensivo de errores y corner cases para garantizar que se pueda ejecutar aun cuando 
    algunos drones no puedan cumplir su recorrido 
    
  ### Datos
    Los archivos con la informacion de las rutas de los drones se encuentra por default en:
    - deliveryData/input
    
    Los archivos con la informacion de las entregas de los drones se encuentra por default en:
    - deliveryData/output
    
   ### Casos de prueba
    - La principal logica de negocio (Facade, Service, Validator) esta validada por 25 casos de prueba
    - Por falta de tiempo no alcance a terminar las pruebas de integracion
    - La aplicacion trae por defecto informacion de rutas para 20 drones. En estas rutas se pueden probar
     todas las reglas de negocio de la aplicacion asi como los corner cases. 
    

