package serializr.ast;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import serializr.test.util.GrammarUtil;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static serializr.test.util.GrammarUtil.toParser;

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
    public void testGetTypeRef() throws Exception {
        fail("TODO");
    }

    @Test
    public void testIsOptionalTrue() throws Exception {
        FieldNode field = parseField("optional myName : Int");
        assertThat(field.isOptional(), is(true));
    }

    private FieldNode parseField(String... lines) throws RecognitionException, IOException {
        return GrammarUtil.parseField(toParser(lines));
    }
}
