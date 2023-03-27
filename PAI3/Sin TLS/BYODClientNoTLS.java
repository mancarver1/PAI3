import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class BYODClientNoTLS {

    public static void main(String[] args) throws IOException {
        // Establecer conexión con el servidor en el puerto 7070
        Socket socket = new Socket("localhost", 7070);

        // Abrir PrintWriter para enviar datos al servidor
        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        // Enviar nombre de usuario, contraseña y mensaje secreto al servidor
        String username = "yourUsername";
        String password = "yourPassword";
        String secretMessage = "yourSecretMessage";
        output.println(username);
        output.println(password);
        output.println(secretMessage);
        output.flush();

        // Abrir BufferedReader para leer datos del servidor
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Leer respuesta del servidor
        String serverResponse = input.readLine();
        System.out.println("Respuesta del servidor: " + serverResponse);

        // Cerrar recursos
        input.close();
        output.close();
        socket.close();
    }
}