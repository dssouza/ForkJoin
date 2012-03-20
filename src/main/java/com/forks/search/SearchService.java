/**
 *
 */
package main.java.com.forks.search;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Raman_Pliashkou
 */
public class SearchService {

    Folder root = null;

    Long countOfOccurrences = null;

    public Folder getRoot() {
        return root;
    }

    public Long getCountOfOccurrences() {
        return countOfOccurrences;
    }

    public void setRoot(Folder root) {
        this.root = root;
    }

    public Long search(String folderName, String searchWord) throws IOException {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        root = Folder.fromDirectory(new File(folderName));
        countOfOccurrences = forkJoinPool.invoke(new FolderSearchTask(root, searchWord));
        return countOfOccurrences;

    }

}
