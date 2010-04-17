package serializr.ast;

import com.google.common.collect.Lists;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import serializr.test.util.GrammarUtil;
import serializr.typesystem.Field;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static serializr.test.util.GrammarUtil.toParser;

/**
 *
 */
public class SequenceNodeTest {

    @Test
    public void testGetName() throws IOException, RecognitionException {
        SequenceNode seq = parseSeq("seq MyName {}");
        assertThat(seq.getName(), is("MyName"));
    }

    @Test
    public void testGetFields() throws Exception {
        SequenceNode seq = parseSeq("seq MyName { aField : Int, anotherField : String };");
        List<Field> fields = Lists.newArrayList(seq.getFields());
        assertThat(fields.size(), is(2));
    }

    private SequenceNode parseSeq(String... lines) throws RecognitionException, IOException {
        return GrammarUtil.parseSeq(toParser(lines));
    }

}
