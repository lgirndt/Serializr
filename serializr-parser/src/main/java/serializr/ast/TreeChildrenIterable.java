package serializr.ast;

import org.antlr.runtime.tree.Tree;

import java.util.Iterator;

/**
 *
 */
class TreeChildrenIterable implements Iterable<Tree> {

    private final Tree tree;

    public TreeChildrenIterable(Tree tree) {
        this.tree = tree;
    }

    @Override
    public Iterator<Tree> iterator() {
        return new TreeChildrenIterator(tree);
    }
}
