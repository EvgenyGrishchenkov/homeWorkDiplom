import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

            // Создание объекта JsonParser
            JsonParser parser = new JsonParser();

            // Преобразование существующей JSON-строки в JsonElement
            JsonElement jsonElement = parser.parse(result);

            // Создание объекта GsonBuilder с включенной опцией форматирования
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Преобразование JsonElement обратно в отформатированную JSON-строку
            String formattedResult = gson.toJson(jsonElement);
            System.out.println("Результат поиска в формате JSON:\n" + formattedResult);

        } catch (IOException e) {
            System.out.println("Ошибка при подключении к серверу");
            e.printStackTrace();
        }
    }
}

