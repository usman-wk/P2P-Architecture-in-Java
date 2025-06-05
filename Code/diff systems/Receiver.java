/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package receiver;

/**
 *
 * @author 35613561
 */
import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Receiver {

    private static final int LISTEN_PORT = 5000; // Change if needed
    private static final String SECRET_KEY = "1234567812345678"; // Must match sender

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(LISTEN_PORT)) {
            System.out.println("Receiver is listening on port " + LISTEN_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String encryptedMessage = in.readLine();
                String decryptedMessage = decrypt(encryptedMessage);

                System.out.println("\nReceived (decrypted): " + decryptedMessage);

                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // AES Decryption method
    private static String decrypt(String encryptedText) {
        try {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            return "[Decryption Error]";
        }
    }
}