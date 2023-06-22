import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        try {
            // Создание серверного сокета
            ServerSocket serverSocket = new ServerSocket(4321);
            System.out.println("Server started");

            while (true) {
                // Ожидание нового клиента
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // Создание потока для чтения сообщений от клиента
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());

                // Создание потока для отправки сообщений клиенту
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                while (true) {
                    // Чтение сообщения от клиента
                    String message = in.readUTF();
                    System.out.println("Message from client: " + message);

                    // Отправка сообщения клиенту
                    out.writeUTF("Server received your message: " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}