# BYOD Mensajería Segura y Prueba de Estrés
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7e1cb01cbaa643b79b4bc0402781d029)](https://app.codacy.com/gh/mancarver1/PAI3/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)


Este proyecto es una colección de sistemas de mensajería que demuestran diferentes niveles de seguridad y rendimiento. Hay tres componentes principales en este proyecto:

1. BYOD Mensajería segura con TLS v1.3

Este sistema de mensajería utiliza el protocolo TLS v1.3 para cifrar la comunicación entre el cliente y el servidor. La comunicación entre el cliente y el servidor está protegida mediante cifrado, lo que garantiza autenticidad, confidencialidad e integridad de los datos.

2. BYOD Mensajería sin TLS

Este sistema de mensajería es una versión simplificada del primer componente. No utiliza cifrado (TLS) para proteger la comunicación entre el cliente y el servidor. El cliente envía un mensaje secreto al servidor, que muestra el mensaje recibido en la consola. Esta implementación no garantiza la confidencialidad ni la integridad de los datos y es adecuada solo para fines educativos y de demostración.

3. Prueba de estrés para 300 clientes simultáneos

Esta parte del proyecto incluye una prueba de estrés para evaluar el rendimiento del servidor cuando se conectan 300 clientes simultáneamente. La prueba se basa en el sistema de mensajería segura con TLS v1.3. Los clientes se conectan al servidor y envían mensajes secretos. Esta prueba de estrés ayuda a evaluar el rendimiento y la capacidad del servidor para manejar un gran número de conexiones simultáneas.

**Notas adicionales:** Cada uno de los componentes mencionados tiene su propio archivo README que proporciona detalles específicos, incluidas instrucciones de compilación y ejecución. Consulte los archivos README individuales para obtener más información.

