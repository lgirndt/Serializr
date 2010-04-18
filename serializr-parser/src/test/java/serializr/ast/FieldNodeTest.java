package serializr.ast;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import serializr.grammar.SerializrParser;
import serializr.typesystem.TypeMatch;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static serializr.ast.GrammarUtil.toParser;

/**
 *
 */
public class FieldNodeTest {

    @Test
    public void testGetName() throws Exception {
        FieldNode field = parseField("myName : Int");
        assertThat(field.getName(), is("myName"));
    }

    @Test
    public void testGetTypeRefOnPrimitive() throws Exception {
        FieldNode field = parseField("myName : Int");
        TypeMatch expected = TypeMatch.createPrimitiveTypeMatch("Int");
        assertNotNull(field.getTypeRef());
        assertThat(field.getTypeRef().getTypeMatch(), is(expected));
    }

    @Test
    public void testIsOptionalTrue() throws Exception {
        FieldNode field = parseField("optional myName : Int");
        assertThat(field.isOptional(), is(true));
    }

    private FieldNode parseField(String... lines) throws RecognitionException, IOException {
        return parseField(toParser(lines));
    }

    private FieldNode parseField(SerializrParser parser) throws RecognitionException {
        return (FieldNode) parser.fieldDeclaration().getTree();
    }
}
