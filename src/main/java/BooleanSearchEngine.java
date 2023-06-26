import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    //???

    Map<String, PageEntry> zaeb = new HashMap<>();
    Map<String, List<PageEntry>> resultZaeb = new HashMap<>();
    // Map<String, Integer> freqs = new HashMap<>();
    //List<PageEntry> spisok = new ArrayList<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File scannedFile : pdfsDir.listFiles()) {

            var doc = new PdfDocument(new PdfReader(scannedFile));
            int numberPages = doc.getNumberOfPages();
            for (int i = 1; i <= numberPages; i++) {

                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");
                // прочтите тут все pdf и сохраните нужные данные,
                // тк во время поиска сервер не должен уже читать файлы

                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                    PageEntry pageEntry = new PageEntry(scannedFile.getName(), i, entry.getValue());

                    zaeb.put(entry.getKey(), pageEntry);
                }
                for (Map.Entry<String, PageEntry> entry : zaeb.entrySet()) {
                    String key = entry.getKey();
                    PageEntry value = entry.getValue();

                    if (!resultZaeb.containsKey(key)) {
                        resultZaeb.put(key, new ArrayList<>());
                    }
                    resultZaeb.get(key).add(value);
                }
                //System.out.println(resultZaeb.get("в"));
            }
        }



    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> result = resultZaeb.get(word);
//                .stream()
//                .filter(pageEntry -> pageEntry.getPageContent().containsWord(word))
//                .map(pageEntry -> new PageEntry(pageEntry.getPageName(), pageEntry.getPageIndex()))
//                .collect(Collectors.toList());
        return result;
    }
}
