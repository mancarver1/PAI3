import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.*;

public class BYOD300Clients implements Runnable {

    private static int counter = 0;
    private int messageId;
    private CountDownLatch latch;

    public BYOD300Clients(int messageId, CountDownLatch latch) {
        this.messageId = messageId;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            // Cargar el keystore y su contraseña desde el archivo "keystore.jks"
            String password = "password";
            KeyStore keystore = KeyStore.getInstance("JKS");
            FileInputStream keystoreFile = new FileInputStream("keystore.jks");
            keystore.load(keystoreFile, password.toCharArray());

            // Crear un SSLContext y un SSLSocketFactory a partir del keystore cargado
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLSocketFactory factory = sslContext.getSocketFactory();

            // Crear un SSLSocket para conectarse al servidor en "localhost" y puerto 7070
            SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 7070);

            // Configurar parámetros de seguridad SSL/TLS en el socket SSL
            SSLParameters sslParams = new SSLParameters();
            String[] protocols = new String[] { "TLSv1.3" };
            String[] cipherSuites = new String[] {
                    "TLS_AES_128_GCM_SHA256",
                    "TLS_AES_256_GCM_SHA384",
                    "TLS_CHACHA20_POLY1305_SHA256"
            };
            sslParams.setProtocols(protocols);
            sslParams.setCipherSuites(cipherSuites);
            socket.setSSLParameters(sslParams);

            // Crear BufferedReader para leer la
            // respuesta del servidor
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Crear PrintWriter para enviar datos al servidor
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String username = "yourUsername";
            String passwordStr = "yourPassword";
            String secretMessage = "Message #" + messageId;

            // Enviar el nombre de usuario, contraseña y mensaje secreto al servidor
            output.println(username);
            output.println(passwordStr);
            output.println(secretMessage);
            output.flush();

            // Leer la respuesta del servidor
            String response = input.readLine();

            // Mostrar la respuesta al usuario
            System.out.println("Mensaje #" + messageId + " - Respuesta del servidor: " + response);

            // Incrementar el contador de mensajes recibidos
            synchronized (BYOD300Clients.class) {
                counter++;
            }

            // Cerrar recursos
            output.close();
            input.close();
            socket.close();

        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException
                | KeyManagementException ioException) {
            ioException.printStackTrace();
        } finally {
            // Indicar que el hilo ha finalizado su ejecución
            latch.countDown();
        }
    }

    public static void main(String[] args) {
        // Crear un objeto CountDownLatch con un contador inicial de 300
        CountDownLatch latch = new CountDownLatch(300);

        // Crear 300 instancias de la clase y ejecutarlas en paralelo
        for (int i = 1; i <= 300; i++) {
            try {
                Thread.sleep((long) (Math.random() * 500)); // Esperar entre 0 y 500 milisegundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new BYOD300Clients(i, latch)).start();
        }

        try {
            // Esperar a que todos los hilos finalicen antes de imprimir el contador
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Imprimir el contador de mensajes recibidos
        System.out.println("Mensajes recibidos: " + counter);
    }
}