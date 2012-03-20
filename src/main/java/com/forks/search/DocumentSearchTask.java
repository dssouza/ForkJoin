/**
 *
 */
package main.java.com.forks.search;

import java.util.concurrent.RecursiveTask;

/**
 * @author Raman_Pliashkou
 */
class DocumentSearchTask extends RecursiveTask<Long> {

    private static final long serialVersionUID = 1L;

    private final Document document;

    private final String searchedWord;

    DocumentSearchTask(Document document, String searchedWord) {
        super();
        this.document = document;
        this.searchedWord = searchedWord;
    }

    @Override
    protected Long compute() {
        Long count = 0L;
        if ((count = WordCounter.occurrencesCount(document, searchedWord)) > 0) {
            this.document.setOccurrences(true);
        } else
            this.document.setOccurrences(false);
        return count;
    }
}
