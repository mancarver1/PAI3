# BYOD Mensajería sin TLS

Este proyecto contiene un sistema de mensajería simple en el que un cliente se comunica con un servidor utilizando sockets sin cifrado (sin TLS). El cliente envía un mensaje secreto al servidor, que imprime una confirmación de llegada por consola. El propósito de este proyecto es demostrar la comunicación básica entre el cliente y el servidor sin cifrado y las vulnerabilidades que conlleva.

## Requisitos
- Java SE Development Kit (JDK) instalado en su máquina

## Compilación
Para compilar los archivos de código fuente de Java, ejecute el siguiente comando:

javac BYODClientNoTLS.java BYODServerNoTLS.java

Esto generará archivos de clases `.class` para los archivos `BYODClientNoTLS.java` y `BYODServerNoTLS.java`.

## Ejecución
### Servidor
Ejecute el siguiente comando para iniciar el servidor:

java BYODServerNoTLS

El servidor escuchará conexiones entrantes en el puerto 7070.

### Cliente
Ejecute el siguiente comando para iniciar el cliente:

java BYODClientNoTLS


## Funcionamiento
1. Inicie el servidor, que escuchará conexiones entrantes en el puerto 7070.
2. Inicie el cliente, que se conectará al servidor y enviará un mensaje secreto.
3. El servidor verificará las credenciales del cliente y mostrará el mensaje secreto recibido en la consola.

Este ejemplo no utiliza cifrado (TLS) para garantizar la comunicación segura entre el cliente y el servidor. Como resultado, los mensajes enviados entre el cliente y el servidor son vulnerables a la interceptación y manipulación por terceros.
