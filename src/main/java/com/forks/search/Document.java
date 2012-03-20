/**
 *
 */
package main.java.com.forks.search;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Raman_Pliashkou
 */
public class Document {
    private final List<String> lines;

    private String name;

    boolean occurrences = false;

    public Document(List<String> lines) {
        this.lines = lines;
    }

    List<String> getLines() {
        return this.lines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOccurrences() {
        return occurrences;
    }

    public void setOccurrences(boolean occurrences) {
        this.occurrences = occurrences;
    }

    static Document fromFile(File file) throws IOException {
        List<String> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }
        return new Document(lines);
    }
}
