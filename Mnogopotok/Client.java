import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            String message, response;
            while (true) {
                System.out.print("Введите сообщение: ");
                message = scanner.nextLine();
                out.println(message);
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
                response = in.readLine();
                System.out.println("" + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}