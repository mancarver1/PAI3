# BYOD Secure Messaging con TLS v1.3

Este proyecto contiene un sistema de mensajería segura en el que un cliente se comunica con un servidor utilizando sockets SSL/TLS. El cliente envía un mensaje secreto al servidor, que imprime una confirmación de llegada por consola. El propósito principal de este proyecto es demostrar cómo usar TLS v1.3 para cifrar la comunicación entre el cliente y el servidor.

## Requisitos
- Java SE Development Kit (JDK) instalado en su máquina
- Crear un archivo keystore.jks utilizando la herramienta keytool de Java

## Compilación
Para compilar los archivos de código fuente de Java, ejecute el siguiente comando:

javac BYODClient.java BYODServer.java

Esto generará archivos de clases .class para los archivos BYODClient.java y BYODServer.java.

## Ejecución
Antes de ejecutar los programas, asegúrese de que tenga un archivo keystore.jks que contenga los certificados y las claves necesarias.

### Servidor
Ejecute el siguiente comando para iniciar el servidor:

java -Djavax.net.ssl.keyStore=keystore.jks -Djavax.net.ssl.keyStorePassword=password BYODServer

Reemplace password con la contraseña del keystore.

### Cliente
Ejecute el siguiente comando para iniciar el cliente:

java -Djavax.net.ssl.trustStore=keystore.jks -Djavax.net.ssl.trustStorePassword=password BYODClient

Reemplace password con la contraseña del keystore.

## Funcionamiento
1. Inicie el servidor, que escuchará conexiones entrantes en el puerto 7070.
2. Inicie el cliente, que se conectará al servidor y enviará un mensaje secreto.
3. El servidor verificará las credenciales del cliente y mostrará el mensaje secreto recibido en la consola.

El proyecto utiliza TLS v1.3 para garantizar una comunicación segura entre el cliente y el servidor. Los sockets SSL/TLS están configurados para utilizar solo el protocolo TLS v1.3 y un conjunto específico de suites de cifrado.
