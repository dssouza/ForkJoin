/**
 *
 */
package main.java.com.forks.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Raman_Pliashkou
 */
public class ContentSearchTest {


    public static void main(String[] args) throws IOException {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        Folder folder = Folder.fromDirectory(new File(args[0]));

        Long countOfOccurrences = forkJoinPool.invoke(new FolderSearchTask(folder, args[1]));

        CompositeIterator<Folder> c = new CompositeIterator<Folder>(folder.createIterator());
        List<String> fileOfOccurrences = new ArrayList<>();
        Folder f = null;
        long countFilesOfOccurrences = 0L;
        while ((f = c.next()) != null) {
            System.out.println(f.getName());
            for (Document doc : f.getDocuments()) {
                System.out.println("----" + doc.getName());

                if (doc.isOccurrences()) {
                    countFilesOfOccurrences++;
                    fileOfOccurrences.add(doc.getName());
                }
            }
        }

        System.out.println("\n\nTotal:");
        System.out.println(countOfOccurrences + " match(es) of occurrence(s)");
        System.out.print(countFilesOfOccurrences + " files");
        for (String filename : fileOfOccurrences) {
            System.out.println(filename);
        }
    }
}
