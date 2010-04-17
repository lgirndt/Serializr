package serializr.ast;

import org.antlr.runtime.tree.CommonTree;
import serializr.typesystem.Field;
import serializr.typesystem.TypeRef;

/**
 *
 */
public class FieldNode extends DefaultNode implements Field {

    public FieldNode(CommonTree node) {
        super(node);
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public TypeRef getTypeRef() {
        return null;
    }

    @Override
    public boolean isOptional() {
        return false;
    }
}
