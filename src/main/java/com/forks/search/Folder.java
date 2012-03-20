/**
 *
 */
package main.java.com.forks.search;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Raman_Pliashkou
 */
public class Folder implements Component<Folder> {

    private String name = null;

    private List<Document> documents;

    private List<Folder> subFolders;

    public Folder() {

    }

    Folder(List<Folder> subFolders, List<Document> documents) {
        this.subFolders = subFolders;
        this.documents = documents;
    }

    public List<Folder> getSubFolders() {
        return this.subFolders;
    }

    public List<Document> getDocuments() {
        return this.documents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Folder fromDirectory(File dir) throws IOException {
        List<Document> documents = new ArrayList<Document>();
        List<Folder> subFolders = new ArrayList<Folder>();
        for (File entry : dir.listFiles()) {
            if (entry.isDirectory()) {
                Folder f = Folder.fromDirectory(entry);
                f.setName(entry.getPath());
                subFolders.add(f);
            } else if (CMatcher.match(entry.getName())) {
                Document doc = Document.fromFile(entry);
                doc.setName(entry.getPath());
                documents.add(doc);
            }
        }
        return new Folder(subFolders, documents);
    }

    public Iterator<Folder> createIterator() {
        return subFolders.iterator();
    }
}
