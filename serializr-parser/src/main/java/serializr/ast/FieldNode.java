package serializr.ast;

import org.antlr.runtime.Token;
import serializr.typesystem.Field;
import serializr.typesystem.TypeRef;

/**
 *
 */
public class FieldNode extends DefaultNode implements Field {
    public FieldNode(Token t) {
        super(t);
    }

    @Override
    public String getName() {
        return getChild(1).getText();
    }

    @Override
    public TypeRef getTypeRef() {
        return null;
    }

    @Override
    public boolean isOptional() {
        // TODO
        return false;
    }
}
