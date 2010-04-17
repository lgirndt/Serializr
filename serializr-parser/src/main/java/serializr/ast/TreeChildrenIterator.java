package serializr.ast;

import org.antlr.runtime.tree.Tree;

import java.util.Iterator;

/**
 *
 */
class TreeChildrenIterator implements Iterator<Tree> {

    private final Tree tree;
    private int index;

    public TreeChildrenIterator(Tree tree) {
        this.tree = tree;
        this.index = 0;
    }


    @Override
    public boolean hasNext() {
        return index < tree.getChildCount();
    }

    @Override
    public Tree next() {
        Tree child = tree.getChild(index);
        index++;
        return child;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
