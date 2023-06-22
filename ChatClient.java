import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {
            // Подключение к серверу
            Socket socket = new Socket("localhost", 4321);

            // Создание потока для чтения сообщений от сервера
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // Создание потока для отправки сообщений на сервер
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                // Чтение сообщения от пользователя
                String message = reader.readLine();

                // Отправка сообщения на сервер
                out.writeUTF(message);

                // Чтение ответа от сервера
                String response = in.readUTF();
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
