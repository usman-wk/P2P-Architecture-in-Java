
package peer1;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Peer1 {

    private static final String SECRET_KEY = "1234567812345678"; // 16-char AES key
    private static final String RECEIVER_IP = "localhost";
    private static final int RECEIVER_PORT = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput;

        System.out.println("Peer (Sender) started. Messages will be sent to localhost:5000");

        while (true) {
            System.out.print("\nEnter message to send (type 'exit' to quit): ");
            userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }

            try (Socket socket = new Socket(RECEIVER_IP, RECEIVER_PORT)) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String encryptedMessage = encrypt(userInput);
                out.println(encryptedMessage);
                System.out.println("Message sent successfully.");
            } catch (IOException e) {
                System.out.println("Error sending message: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Peer stopped.");
    }

    private static String encrypt(String plainText) {
        try {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }
}
