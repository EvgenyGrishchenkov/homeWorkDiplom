import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class SearchClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 8989); // подключаемся к серверу на локальном хосте
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println("Введите запрос:");
            String query = consoleIn.readLine(); // читаем запрос с консоли

            out.println(query); // отправляем запрос на сервер
            out.flush();

            String result = in.readLine(); // получаем результат от сервера
            System.out.println("Результат поиска: " + result);
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к серверу");
            e.printStackTrace();
        }
    }
}
