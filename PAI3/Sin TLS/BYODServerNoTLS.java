import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BYODServerNoTLS {

    public static void main(String[] args) throws IOException, InterruptedException {
        try (// Crear un ServerSocket en el puerto 7070
        ServerSocket serverSocket = new ServerSocket(7070)) {
            while (true) {

                System.err.println("Waiting for connection...");

                // Esperar y aceptar conexiones de clientes
                Socket socket = serverSocket.accept();
                // Abrir BufferedReader para leer datos del cliente
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Leer nombre de usuario, contraseña y mensaje secreto del cliente
                String username = input.readLine();
                String password = input.readLine();
                String secretMessage = input.readLine();

                // Abrir PrintWriter para enviar datos al cliente
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                // Verificar las credenciales y enviar la respuesta al cliente
                if (username != null && password != null) {
                    // En un escenario real, se verificaría el nombre de usuario y la contraseña en
                    // una base de datos o algún otro almacenamiento seguro
                    output.println("Su mensaje secreto ha sido recibido correctamente.");
                    System.out.println("Mensaje secreto recibido de " + username);
                } else {
                    output.println("Error: No se pudo almacenar el mensaje secreto.");
                }

                // Cerrar recursos
                output.close();
                input.close();
                socket.close();
            }
        }
    }
}
