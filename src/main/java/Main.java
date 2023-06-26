import java.io.*;


public class Main {
    public static void main(String[] args) throws Exception {

        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        SearchServer server = new SearchServer();
        //System.out.println(engine.search("бизнес"));


    }
}
