import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class SearchClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 8989);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println("Введите запрос:");
            String query = consoleIn.readLine();

            out.println(query);
            out.flush();

            String result = in.readLine();
            System.out.println("Результат поиска: " + result);
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к серверу");
            e.printStackTrace();
        }
    }
}
