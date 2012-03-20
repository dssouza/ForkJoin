/**
 *
 */
package main.java.com.forks.search;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Raman_Pliashkou
 */
@SuppressWarnings("unchecked")
public class CompositeIterator<T extends Component<T>> implements Iterator<T> {
    @SuppressWarnings("rawtypes")
    Stack<Iterator<? extends T>> stack = new Stack();

    public CompositeIterator(Iterator<? extends T> iterator) {
        stack.push(iterator);
    }

    public T next() {
        if (hasNext()) {
            Iterator<? extends T> iterator = stack.peek();
            T component = iterator.next();
            stack.push(component.createIterator());
            return component;
        } else {
            return null;
        }
    }

    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            Iterator<? extends T> iterator = stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
