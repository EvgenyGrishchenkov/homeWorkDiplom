import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> resultZZZ = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File scannedFile : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(scannedFile));
            int numberPages = doc.getNumberOfPages();
            for (int i = 1; i <= numberPages; i++) {
                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                    String word = entry.getKey();
                    int frequency = entry.getValue();

                    PageEntry pageEntry = new PageEntry(scannedFile.getName(), i, frequency);

                    if (!resultZZZ.containsKey(word)) {
                        resultZZZ.put(word, new ArrayList<>());
                    }
                    List<PageEntry> pageEntries = resultZZZ.get(word);
                    int index = Collections.binarySearch(pageEntries, pageEntry);
                    if (index < 0) {
                        index = -index - 1;
                    }
                    pageEntries.add(index, pageEntry);

                }
            }
        }
    }
    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> result = resultZZZ.get(word);
        return result;
    }
}
