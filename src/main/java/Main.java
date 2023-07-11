import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
            System.out.println("Сервер запущен");

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String query = in.readLine();
                    System.out.println("Получен запрос: " + query);

                    List<PageEntry> result = engine.search(query);

                    Gson gson = new Gson();
                    String jsonResult = gson.toJson(result);

                    out.println(jsonResult);
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