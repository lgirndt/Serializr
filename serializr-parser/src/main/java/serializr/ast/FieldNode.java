package serializr.ast;

import org.antlr.runtime.Token;
import serializr.typesystem.Field;
import serializr.typesystem.TypeRef;

/**
 *
 */
class FieldNode extends Node implements Field {
    public FieldNode(Token t) {
        super(t);
    }

    @Override
    public String getName() {
        return getChild(1).getText();
    }

    @Override
    public TypeRef getTypeRef() {
        return (TypeRef) getChild(0);
    }

    private ModifierNode getModifiers() {
        return (ModifierNode) getChild(2);
    }

    @Override
    public boolean isOptional() {
        return getModifiers().containsOptional();
    }
}
