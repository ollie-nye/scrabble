package data;

import java.io.Serializable;

/**
 * Superclass representing a Tuple that contains 2 related objects.
 * @author Thomas Geraghty
 * @version 1.0
 */
public class Tuple<L, R> implements Serializable{

    private final L left;
    private final R right;

    public Tuple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() { return left; }
    public R getRight() { return right; }

    @Override
    public String toString() {
        return "(" + left + "," + right + ")";
    }
}
