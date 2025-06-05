package peer2;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Peer2 {

    private static final int LISTEN_PORT = 5000;
    private static final String SECRET_KEY = "1234567812345678"; // 16-char AES key

    public static void main(String[] args) {
        System.out.println("Receiver is starting...");

        try (ServerSocket serverSocket = new ServerSocket(LISTEN_PORT)) {
            System.out.println("Receiver is listening on port " + LISTEN_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String encryptedMessage = in.readLine();
                if (encryptedMessage != null) {
                    String decryptedMessage = decrypt(encryptedMessage);
                    System.out.println("\nReceived (decrypted): " + decryptedMessage);
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

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
