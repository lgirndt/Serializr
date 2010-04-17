package serializr.ast;

import org.antlr.runtime.Token;
import serializr.typesystem.Type;
import serializr.typesystem.TypeRef;

/**
 *
 */
public class TypeRefNode extends DefaultNode implements TypeRef {

    public TypeRefNode(Token t) {
        super(t);
    }

    @Override
    public Type getSerializrType() {
        return null;
    }

}
