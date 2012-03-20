/**
 *
 */
package main.java.com.forks.search;

import java.util.Iterator;

/**
 * @author Raman_Pliashkou
 */
public interface Component<T> {
    public Iterator<T> createIterator();

}
