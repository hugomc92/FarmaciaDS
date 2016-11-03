FarmaciaDS
=================

Sistema de farmacias para la Práctica 2 de la asignatura Desarrollo del Software.

### **Autores: Román Arranz Guerrero y Hugo Maldonado Cózar**

##Arquitectura Software

###Protocolo para obtener la representación de una Arquitectura Software 

1. Definir los aspectos estructurales como una composición de componentes
2. Las estructuras globales de control
3. Los protocolos de comunicación
4. La sincronización y acceso a los datos
5. La asignación de la funcionalidad a los elementos del diseño
6. La composición de estos elementos
7. Su distribución física
8. Su escalabilidad y su desempeño  

###Descripción de una Arquitectura Software

1. Guiar en la construcción y mantenimiento del sistema 
2. Ayudar a planear los costes y evolución del sistema 
3. Servir como un medio para el análisis, evaluación o comparación de arquitecturas software 
4. Facilitar la comunicación entre las partes interesadas en las arquitecturas y los sistemas 
5. Documentar el conocimiento arquitectónico más allá del ámbito de los proyectos individuales 
6. Capturar idiomas arquitectónicos reutilizables (tales como estilos arquitectónicos y patrones) 

Se pueden utilizar lenguajes de propósito general como **UML como ADLs** así como para modelar procesos de negocio y similares. 

###Ejemplos de Estilos Arquitectónicos
Pipes and filters, Tipos de datos abstractos y OO, Repositorios, Capas, Basados en Eventos, Interpretes, etc.

###Estilo Arquitectónico elegido

Usaremos una **Arquitectura Centradas en los Datos** ya que vamos a tener una base de datos en el servidor a la que van a acceder los clientes web y sobre la que vamos a realizar sincronizaciones con la base de datos de SQLite en los dispositivos Android. Además las aplicaciones de los clientes Android acceden a los datos y ejecutan sus propias operaciones.

### Cliente -> Android en principio

### Gestión -> Web en principio
Si nos da tiempo, haremos los dos roles en las dos plataformas

##Patrones que vamos a usar

###Aplicación Android y Web
- `Singleton` para crear el conector BD local, el conector BD del servidor y la Cesta de productos
- `Factoría` para la creación de los Productos.
- `Fachada` en conector de base de datos
- `Inmutable` Producto solo en el cliente app Android.
- `Delegación` para cargar catalogo al seleccionar farmacia para visualizar posteriormente al pinchar en mostrar catalogo, para la carga de puntos Google Maps y para el conector BD a otra clase que hace las operaciones
- Patrón `Bridge` para implementación de los métodos de cliente y administrador (abstracta Usuario), también se podía haber usado `Factoria`
- `Observable Observador` ya que varios clientes tienen reservas de productos, con este patron podemos informar a todos cuando estén disponibles por e-mail. Sólo en el Servidor si no hacemos el rol user en el Server
- `Visitante` realizar la misma operacion en cada farmacia y acumular el resultado. Sólo en Android si no hacemos el rol admin en Android
- `View Holder` para realizar la carga del cotenido de la base de datos en la vista al usuario

##Consultas al profesor

Tenemos que hacer los diagramas de casoss de uso y el de secuencia para la interacción entre la aplicacion y el cliente web.

##Clases

*Tanto la clase* ***Cliente*** *como la clase* ***Administrador*** *que serían especializaciones de Usuario sobrarían usando el patrón de diseño* `Bridge`

###Aspectos comunes a Clientes y Administradores
- Atributos
	- Ubicación
	- Email
	- Contraseña
	- Lista de productos actual
	- Nombre
	- Apellidos
	- Ult Conexión

###Clase Farmacia
- Atributos
	- CIF
	- Ubicación
	- Horario
	- Nombre
	- Personal
	- Telefono
	- Email
	- ListaServicios

## ULTIMAS CONSIDERACIONES

- TABLA FARMACIA 
	- latitud      float
	- longitud   float
	- dirección varchar


- INVENTARIO
	- clave compuesta IDPRODUCT, IDFARMACIA
	
- PRODUCTO
	- IDCATEGORIA - FOREIGN KEY

- CATEGORIA
	- ID
	- NAME
	- IMG

- PEDIDO (se resta stock de ese producto en esa farmacia cuando se complete)
	- KEY (EMAIL, IDPRODUCT, IDFARMACIA )
	- CANTIDAD

- RESERVA
	- KEY (EMAIL, IDPRODUCT, IDFARMACIA)
	- CANTIDAD
	- NOTIFICADO

se le debe ofrecer la oportunidad de cancelarla o confirmarla.

EN LA APLICACION SE MOSTRARA STOCK DE LOS PRODUCTOS PERO DESCONTANDO LAS UNIDADES RESERVADAS.


**PARTE CLIENTE (ANDROID)**
	
- CONSULTA USUARIO -> guardado en system preferences
- SYNC FARMACIA
- SYNC CATEGORIA
- SYNC  PRODUCTOS	
- SYNC INVENTARIO
- ACTUALIZAR PEDIDOS -> ACTUALIZAR STOCKAJE DE PRODUCTOS EN EL INVENTARIO
- ACTUALIZAR RESERVAS

##Referencias

- [Patrones Software](https://sourcemaking.com/design_patterns)



## Licencia

Los detalles se encuentran en el archivo `LICENSE`. En resumen, todo el contenido tiene como licencia **MIT License** por lo tanto es totalmente gratuito su uso para proyectos privados o de uso comercial.