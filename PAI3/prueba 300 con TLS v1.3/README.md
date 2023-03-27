# BYOD 300 Secure Messaging TEST

Este proyecto contiene una prueba de un sistema de mensajería segura para 300 clientes que se comunican con un servidor utilizando sockets SSL/TLS. Los clientes envían mensajes secretos al servidor, que los almacena en un archivo CSV.

## Requisitos
- Java SE Development Kit (JDK) instalado en su máquina
- Crear un archivo `keystore.jks` utilizando la herramienta `keytool` de Java

## Compilación
Para compilar los archivos de código fuente de Java, ejecute el siguiente comando:

javac BYOD300Clients.java BYOD300Server.java


Esto generará archivos de clases `.class` para los archivos `BYOD300Clients.java` y `BYOD300Server.java`.

## Ejecución
Antes de ejecutar los programas, asegúrese de que tenga un archivo `keystore.jks` que contenga los certificados y las claves necesarias.

### Servidor
Ejecute el siguiente comando para iniciar el servidor:

java -Djavax.net.ssl.keyStore=keystore.jks -Djavax.net.ssl.keyStorePassword=password BYOD300Server


Reemplace `password` con la contraseña del keystore.

### Clientes
Ejecute el siguiente comando para iniciar el cliente:

java -Djavax.net.ssl.trustStore=keystore.jks -Djavax.net.ssl.trustStorePassword=password BYOD300Clients


Reemplace `password` con la contraseña del keystore.

## Funcionamiento

1. Inicie el servidor, que escuchará conexiones entrantes en el puerto 7070.
2. Inicie el cliente, que se conectará al servidor y enviará mensajes secretos.
3. El servidor verificará las credenciales del cliente y almacenará los mensajes secretos en un archivo CSV llamado `messages.csv`.

## Notas adicionales

- Asegúrese de mantener protegidos los archivos de keystore y las contraseñas para evitar la exposición de datos confidenciales.
