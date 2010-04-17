package serializr.ast;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import serializr.grammar.SerializrParser;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static serializr.test.util.GrammarUtil.parseSeq;
import static serializr.test.util.GrammarUtil.toParser;

/**
 *
 */
public class SequenceNodeTest {

    @Test
    public void testGetName() throws IOException, RecognitionException {
        SerializrParser parser = toParser("seq MyName {}");
        SequenceNode seq = parseSeq(parser);
        assertThat(seq.getName(), is("MyName"));
    }

}
