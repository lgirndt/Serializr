package serializr.ast;

import serializr.grammar.SerializrParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 */
public class GrammarAssert {
    public static void assertValidParsing(SerializrParser parser) {
        assertEquals(0, parser.getOccuredErrors().size());
    }

    public static void assertInvalidParsing(SerializrParser parser) {
        if (parser.getOccuredErrors().size() == 0) {
            fail("Expected parser to fail.");
        }
    }
}
