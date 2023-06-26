import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class SearchServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
            System.out.println("Сервер запущен");

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String query = in.readLine(); // получаем запрос от клиента
                    System.out.println("Получен запрос: " + query);

                    String result = engine.search(query).toString(); // передаем запрос в поисковый движок
                    System.out.println("Результат поиска: " + result);

                    out.println(result); // отправляем результат обратно клиенту
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Ошибка при обработке подключения");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
