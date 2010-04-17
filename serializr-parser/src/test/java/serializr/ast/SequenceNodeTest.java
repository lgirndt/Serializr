package serializr.ast;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import serializr.grammar.SerializrParser;
import serializr.test.util.GrammarUtil;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class SequenceNodeTest {

    @Test
    public void testGetName() throws IOException, RecognitionException {
        SerializrParser parser = GrammarUtil.toParser("seq MyName {}");
        SequenceNode seq = (SequenceNode) parser.seqDeclaration().getTree();
        assertThat(seq.getName(), is("MyName"));
    }

}
