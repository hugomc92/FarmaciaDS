# Instalación y Pruebas

Como se puede observar en el repositorio tenemos diferentes carpeta enumeradas de tal manera que en cada una tenemos la versión ampliada de la anterior.

## Descripcion de las Carpetas

1. **RESTAPI** es la API REST llana con un ejemplo sencillo usando [Jersey (JAX-RS)](https://jersey.java.net).
2. 	**RESTAPIMySQL** es la anterior añadiendo la conexion a MySQL para obtener los datos, como conector usamos [Hibernate](http://hibernate.org) y la configuración del conector está dentro del archivo `hibernate.cfg.xml`.
3. **RESTAPIMySQLWebService** es la anterior añadiendo el servicio web con HTML, hojas de estilo CSS con [Bootstrap](http://getbootstrap.com) y un consumidor de los recursos REST a través de AJAX en el script `script.js`.
4. **RESTAPIMySQLServlets** es la versión definitiva más simple que podemos usar a la hora de desarrollar nuestra aplicación ya que contiene todos los recursos que vamos a usar, tiene configurado 2 servlets en el archivo `web.xml`, uno para la API REST y otro para un simple formulario de JSP.


## Despliegue del Proyecto

Para instalar el proyecto tan solo tenemos que configurar el servidor de [Apache Tomcat 7](http://apache.rediris.es/tomcat/tomcat-7/v7.0.69/bin/apache-tomcat-7.0.69.zip) en el IDE de `Eclipse`.

**Nota:** El servidor válido que es capaz de cargar Servlets de Java y JSP es `Apache Tomcat` por simplicidad, aunque otras opciones son válidas como `NGINX` o `Apache2` pero necesitaremos configurarlos cosa que con Apache Tomcat no tenemos que hacer.

Una vez que tengamos hecha la instalación del servicio HTTP podremos desplegar el proyecto importando el archivo `.WAR` que contiene el mismo.

Tenemos dos formas de hacerlo:

- Desde el propio IDE **(Recomendable)**
- Copiando el war en la carpeta `webapps` de Tomcat.

Si elegimos la opción del IDE solo tenemos que importar el proyecto.

## Dependencias

Las dependencias se resuelven automáticamente ya que se ha usado [Maven](https://maven.apache.org) para obtener las depedencias online.

En caso de que surjan conflictos con las dependencias lo más seguro es instalarlas nuevamente usando Maven ya que nos resuelve los conflictos automáticamente y las dependencias de terceros de esa librería.

Para ello hacemos **Clic Derecho en el Proyecto Importado -> Maven -> Add Dependency**.

Y sugiero copiar las mismas dependencias que hubiera en el archivo `pom.xml`.

