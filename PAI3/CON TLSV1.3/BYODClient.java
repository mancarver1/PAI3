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

import javax.net.ssl.*;
// import javax.swing.JOptionPane;

public class BYODClient {

    public static void main(String[] args) {
        try {
            // Obtener la ruta del archivo del truststore y su contraseña desde las propiedades del sistema
            String truststorePassword = System.getProperty("javax.net.ssl.trustStorePassword");
            String truststorePath = System.getProperty("javax.net.ssl.trustStore");

            // Cargar el truststore desde la ruta y la contraseña proporcionadas
            KeyStore truststore = KeyStore.getInstance("JKS");
            FileInputStream truststoreFile = new FileInputStream(truststorePath);
            truststore.load(truststoreFile, truststorePassword.toCharArray());

            // Crear un SSLContext y un SSLSocketFactory a partir del truststore cargado
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(truststore);
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

            // Crear BufferedReader para leer la respuesta del servidor
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Crear PrintWriter para enviar datos al servidor
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            /* // Solicitar al usuario su nombre de usuario, contraseña y mensaje secreto
            String username = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario:");
            String password = JOptionPane.showInputDialog(null, "Ingrese su contraseña:");
            String secretMessage = JOptionPane.showInputDialog(null, "Ingrese su mensaje secreto:"); */

            String username = "yourUsername";
            String passwordStr = "yourPassword";
            String secretMessage = "yourSecretMessage";

            // Enviar el nombre de usuario, contraseña y mensaje secreto al servidor
            output.println(username);
            output.println(passwordStr);
            output.println(secretMessage);
            output.flush();

            // Leer la respuesta del servidor
            String response = input.readLine();

            // Mostrar la respuesta al usuario
            // JOptionPane.showMessageDialog(null, response);
            System.out.println("Respuesta del servidor: " + response);
            // Cerrar recursos
            output.close();
            input.close();
            socket.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
