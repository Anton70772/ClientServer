import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            out.println("Сервер запущен");

            ExecutorService executor = Executors.newFixedThreadPool(10);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;

                out.println("Введите логин: ");

                out.println("Введите пароль: ");


                while ((message = in.readLine()) != null) {
                    System.out.println("Получено сообщение от клиента: " + message);
                    if ("Привет".equalsIgnoreCase(message)) {
                        out.println("Привет, чем я могу вам помочь?");
                    } else if ("exit".equalsIgnoreCase(message)) {
                        break;
                    } else {
                        out.println("Сервер получил сообщение: " + message);
                    }
                }
                try {

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "password");

                    // чтение данных из запроса от клиента
                    String login = in.readLine();
                    String password = in.readLine();

                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE login=root AND password=234565");
                    stmt.setString(1, login);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        // если нашлись записи в базе данных, то логин и пароль верны
                        out.println("Вход выполнен для пользователя " + login);
                    } else {
                        // если записи не найдены, то логин и пароль не верны
                        out.println("Ошибка входа для пользователя " + login);
                    }

                    // закрытие соединения с базой данных
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}