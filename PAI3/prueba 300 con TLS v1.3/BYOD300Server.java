import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.*;

public class BYOD300Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        PrintWriter csvWriter = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            // Crear archivo CSV para almacenar los mensajes
            fileWriter = new FileWriter("messages.csv", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            csvWriter = new PrintWriter(bufferedWriter);

            // Escribir encabezado en el archivo CSV
            csvWriter.println("Fecha,Hora,Nombre de usuario,Mensaje secreto");

            String keystorePassword = System.getProperty("javax.net.ssl.keyStorePassword");
            String keystorePath = System.getProperty("javax.net.ssl.keyStore");

            // Cargar el keystore desde la ruta y la contraseña proporcionadas
            KeyStore keystore = KeyStore.getInstance("JKS");
            FileInputStream keystoreFile = new FileInputStream(keystorePath);
            keystore.load(keystoreFile, keystorePassword.toCharArray());
            

            // Crear un SSLContext y un SSLServerSocketFactory a partir del keystore cargado
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keystore, keystorePassword.toCharArray());
            sslContext.init(kmf.getKeyManagers(), null, null);
            SSLServerSocketFactory factory = sslContext.getServerSocketFactory();

            // Crear un SSLServerSocket en el puerto 7070
            SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(7070);

            // Configurar parámetros de seguridad SSL/TLS en el servidor SSL
            SSLParameters sslParams = new SSLParameters();
            String[] protocols = new String[] { "TLSv1.3" };
            String[] cipherSuites = new String[] {
                    "TLS_AES_128_GCM_SHA256",
                    "TLS_AES_256_GCM_SHA384",
                    "TLS_CHACHA20_POLY1305_SHA256"
            };
            sslParams.setProtocols(protocols);
            sslParams.setCipherSuites(cipherSuites);
            serverSocket.setSSLParameters(sslParams);

            while (true) {
                System.err.println("Waiting for connection...");

                // Esperar y aceptar conexiones de clientes
                SSLSocket socket = (SSLSocket) serverSocket.accept();

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
                    // En un// escenario real, se verificaría el nombre de usuario y la contraseña
                    // en
                    // una base de datos o algún otro almacenamiento seguro
                    output.println("Su mensaje secreto ha sido recibido correctamente.");
                    System.out.println("Mensaje secreto recibido de " + username );
                    // Obtener la fecha y hora actual
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    String currentDate = dateFormat.format(new Date());
                    String currentTime = timeFormat.format(new Date());
                    // Escribir mensaje en el archivo CSV
                    csvWriter.println(currentDate + "," + currentTime + "," + username);
                    csvWriter.flush(); // forzar la escritura en el archivo inmediatamente
                } else {
                    output.println("Error: No se pudo almacenar el mensaje secreto.");
                }

                // Cerrar recursos
                output.close();
                input.close();
                socket.close();
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException
                | CertificateException | UnrecoverableKeyException | KeyManagementException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Cerrar el archivo CSV
            if (csvWriter != null) {
                csvWriter.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }
}
