
package peer;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.crypto.*; // ADDED
import javax.crypto.spec.SecretKeySpec; // ADDED
import java.util.Base64; // ADDED

public class Peer {
    private static final int LISTEN_PORT = 5000;
    private static final String SECRET_KEY = "1234567812345678"; // ADDED (16-char key for AES)

    public static void main(String[] args) throws IOException {
        // Start listening thread
        ServerSocket serverSocket = new ServerSocket(LISTEN_PORT);
        System.out.println("Peer is listening on port " + LISTEN_PORT);

        Thread serverThread = new Thread(() -> {
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                    String encryptedMessage = in.readLine();
                    String decryptedMessage = decrypt(encryptedMessage); // ADDED
                    System.out.println("\nReceived (decrypted): " + decryptedMessage); // MODIFIED
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        // User input for sending messages
        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true) {
            System.out.print("\nEnter message to send (type 'exit' to quit): ");
            userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter receiver IP address: "); // ADDED
            String ip = scanner.nextLine(); // ADDED

            System.out.print("Enter receiver port: "); // ADDED
            int port = Integer.parseInt(scanner.nextLine()); // ADDED

            try (Socket socket = new Socket(ip, port)) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String encryptedMessage = encrypt(userInput); // ADDED
                out.println(encryptedMessage); // MODIFIED
            } catch (IOException e) {
                System.out.println("Error sending message: " + e.getMessage());
            }
        }

        scanner.close();
        serverSocket.close();
        System.out.println("Peer stopped.");
    }

    // ADDED: Encrypt message using AES
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

    // ADDED: Decrypt message using AES
    private static String decrypt(String encryptedText) {
        try {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }
}
